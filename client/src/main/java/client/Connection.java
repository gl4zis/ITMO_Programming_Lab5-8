package client;

import exceptions.UnavailableServerException;
import general.OsUtilus;
import commands.CommandValidator;
import network.Request;
import network.Response;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.InputConsoleReader;
import parsers.InputScriptReader;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Realization of connection to the server.
 * Requests, responses etc
 */
public class Connection {
    private static final Logger LOGGER = LogManager.getLogger(Connection.class);
    private final InetSocketAddress address;
    private ByteBuffer buffer = ByteBuffer.allocate(100 * 1024);

    /**
     * Standard constructor
     *
     * @param host localhost
     * @param port server port
     */
    public Connection(InetAddress host, int port) {
        address = new InetSocketAddress(host, port);
    }

    /**
     * Reads console and execute command in console line, throw server
     */
    public void run() {
        while (true) {
            System.out.print("-> ");
            String line = InputConsoleReader.readNextLine();
            String output = CommandProcessor.execute(this, line);
            if (output == null)
                break;
            if (!output.isEmpty())
                System.out.println(output);
        }
    }

    /**
     * Sends request in the channel
     */
    private void sendRequest(Request request, DatagramChannel channel) throws IOException {
        buffer = ByteBuffer.wrap(SerializationUtils.serialize(request));
        channel.send(buffer, address);
        LOGGER.debug("Request was sent");
    }

    /**
     * Receives data packs, configure it to one byte[] and put it in buffer
     *
     * @return true if server replied
     */
    private boolean receivePacks(DatagramChannel channel) throws IOException {
        int packNumber = 0;
        byte[] replyArr = null;
        int bytes; //Maximum weight of data in packet for UDP
        if (OsUtilus.IsWindows()) bytes = 65507;
        else bytes = 9216;
        do {
            ByteBuffer partOfBuffer = ByteBuffer.allocate(bytes);
            if (!waitResponse(channel, partOfBuffer))
                return false;
            LOGGER.debug(String.format("Pack number %d, was received (%d bytes)", packNumber + 1, partOfBuffer.capacity()));
            packNumber++;
            replyArr = sumPacks(replyArr, partOfBuffer);
        } while (!checkPacks(replyArr));
        LOGGER.debug("Reply was received");
        return true;
    }

    /**
     * Checks if there is end of the packs of data
     *
     * @return true if all data was got
     */
    private boolean checkPacks(byte[] summator) {
        try {
            SerializationUtils.deserialize(summator);
            buffer = ByteBuffer.wrap(summator);
            return true;
        } catch (SerializationException e) {
            return false;
        }
    }

    /**
     * Concatenates byte[] and ByteBuffer.
     * Needs for sum all packs of data in one byte[]
     */
    private byte[] sumPacks(byte[] summator, ByteBuffer bufferPart) {
        if (summator == null)
            summator = bufferPart.array();
        else
            summator = ArrayUtils.addAll(summator, bufferPart.array());
        return summator;
    }

    /**
     * Listen channel and tries to receive response for 1 second.
     * Checks command in console if consoleBlock == false
     *
     * @param packBuffer where will be received response
     * @return true if server replied
     */
    private boolean waitResponse(DatagramChannel channel, ByteBuffer packBuffer) throws IOException {
        long requestTime = new Date().getTime();
        SocketAddress from;
        boolean respond = false;
        while (!respond) {
            from = channel.receive(packBuffer);
            respond = (from != null);
            if (new Date().getTime() - requestTime > 1000) {
                return false;
            }
        }
        return true;
    }

    /**
     * Opens channel, sends request and gets response from server
     *
     * @return message from request
     */
    private String sendReqGetResp(Request request) throws UnavailableServerException {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            sendRequest(request, channel);
            if (receivePacks(channel)) {
                Response response = SerializationUtils.deserialize(buffer.array());
                return response.message();
            } else throw new UnavailableServerException("Reply will be shown, when server starts reply");
        } catch (IOException e) {
            LOGGER.fatal("Something went wrong: " + e.getMessage());
            return "";
        }
    }

    /**
     * Gathers request from a line, opens channel, sends request and gets response from server.
     * If server is unavailable, waits until it reply
     *
     * @return message from response
     */
    public String sendReqGetResp(String line, InputScriptReader reader) {
        String output;
        boolean message = true;
        while (true) {
            try {
                output = sendReqGetResp(CommandValidator.validCommand(line, reader));
                break;
            } catch (UnavailableServerException e) {
                waitingServer(message, e);
                message = false;
            }
        }
        return output;
    }

    /**
     * Processes waiting reply from server
     * (Prints messages, checks console)
     */
    public static void waitingServer(boolean printMessage, Exception e) {
        if (printMessage) {
            System.out.println(e.getMessage());
            System.out.println("You can only exit from the app");
            System.out.print("-> ");
        }
        String line = InputConsoleReader.checkConsole();
        if (line != null) {
            if (line.equals("exit")) {
                System.exit(0);
            }
            System.out.println("I can't execute commands now =(");
            System.out.print("-> ");
        }
    }
}