package client;

import exceptions.UnavailableServerException;
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

/**
 * Realization of connection to the server.
 * Requests, responses etc
 */
public class Connection {
    private static final Logger LOGGER = LogManager.getLogger(Connection.class);
    private final InetSocketAddress address;
    private Request lastRequest;
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
            try {
                System.out.print("-> ");
                String line = InputConsoleReader.readNextLine();
                String output = CommandProcessor.execute(this, line);
                if (output == null)
                    break;
                if (!output.equals(""))
                    System.out.println(output);
            } catch (UnavailableServerException e) {
                System.out.println(e.getMessage());
                System.out.println(sendReqGetResp());
            }
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
    private boolean receivePacks(DatagramChannel channel, boolean consoleBlock) throws IOException {
        int packNumber = 0;
        byte[] replyArr = null;
        do {
            ByteBuffer partOfBuffer = ByteBuffer.allocate(65507);
            if (!waitResponse(channel, consoleBlock, partOfBuffer))
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
        } catch (SerializationException ignored) {
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
    private boolean waitResponse(DatagramChannel channel, boolean consoleBlock, ByteBuffer packBuffer) throws IOException {
        long requestTime = new Date().getTime();
        SocketAddress from;
        boolean respond = false;
        while (!respond) {
            if (!consoleBlock) {
                String line = InputConsoleReader.checkConsole();
                if (line != null && line.equals("exit"))
                    System.exit(0);
            }
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
    private String sendReqGetResp(Request request, boolean consoleBlock) throws UnavailableServerException {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            lastRequest = request;
            sendRequest(request, channel);
            if (receivePacks(channel, consoleBlock)) {
                Response response = SerializationUtils.deserialize(buffer.array());
                lastRequest = null;
                return response.message();
            } else throw new UnavailableServerException();
        } catch (IOException e) {
            return "Something went wrong: " + e.getMessage();
        }
    }

    /**
     * Opens channel, sends request and gets response from server.
     * Needs for repeats requests, if server didn't reply
     */
    private String sendReqGetResp() {
        while (true) {
            try {
                String message = sendReqGetResp(lastRequest, false);
                System.out.println("Server alive again!");
                return message;
            } catch (UnavailableServerException ignored) {
            }
        }
    }

    /**
     * Gathers request from a line, opens channel, sends request and gets response from server
     *
     * @return message from response
     * @throws UnavailableServerException if server didn't reply
     */
    public String sendReqGetResp(String line) throws UnavailableServerException {
        Request request = new Request(line, null);
        return sendReqGetResp(request, true);
    }

    /**
     * Gathers request from a line, opens channel, sends request and gets response from server.
     * Using for executing commands from script
     *
     * @return message from response
     * @throws UnavailableServerException if server didn't reply
     */
    public String sendReqGetResp(String line, InputScriptReader reader) throws UnavailableServerException {
        Request request = new Request(line, reader);
        return sendReqGetResp(request, true);
    }
}