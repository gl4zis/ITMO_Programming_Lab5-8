package client;

import commands.CommandValidator;
import exceptions.ExitException;
import exceptions.UnavailableServerException;
import general.OsUtilus;
import network.Request;
import network.Response;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.MyScanner;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;

/**
 * Realization of connection to the server.
 * Requests, responses etc
 */
public class Connection {
    private static final Logger LOGGER = LogManager.getLogger(Connection.class);
    private static final int MAX_UDP_BYTES_WINDOWS = 65507;
    private static final int MAX_UDP_BYTES_UNIX = 9216;
    private static final MyScanner CONSOLE = new MyScanner(System.in);
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
     * Processes waiting reply from server
     * (Prints messages, checks console)
     */
    public static void waitingServer(boolean printMessage, Exception e) {
        if (printMessage) {
            System.out.println(e.getMessage());
            System.out.println("You can only exit from the app");
            System.out.print("-> ");
        }
        String line = CONSOLE.checkConsole();
        if (line != null) {
            line = line.split("\n")[0].trim();
            if (line.equals("exit")) {
                throw new ExitException();
            }
            if (!line.isEmpty())
                System.out.println("Can't execute commands now =(");
            System.out.print("-> ");
        }
    }

    /**
     * Reads console and execute command in console line, throw server
     */
    public void run() {
        while (true) {
            System.out.print("-> ");
            String line = CONSOLE.nextLine();
            String output = CommandProcessor.execute(this, line);
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
        int bytes;
        if (OsUtilus.IsWindows()) bytes = MAX_UDP_BYTES_WINDOWS;
        else bytes = MAX_UDP_BYTES_UNIX;
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
    public String sendReqGetResp(String line, MyScanner reader) {
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
}