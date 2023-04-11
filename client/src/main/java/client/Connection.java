package client;

import exceptions.UnavailableServerException;
import network.Request;
import network.Response;
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
            if (!output.equals(""))
                System.out.println(output);
        }
    }

    private void sendRequest(Request request, DatagramChannel channel) throws IOException {
        buffer = ByteBuffer.wrap(SerializationUtils.serialize(request));
        channel.send(buffer, address);
        LOGGER.debug("Request was sent");
    }

    /**
     * Reads channel for a second and receives response if server replied
     *
     * @return true if server replied
     */
    private boolean waitResponse(DatagramChannel channel) throws IOException {
        long requestTime = new Date().getTime();
        boolean respond = false;
        SocketAddress from;
        while (!respond) {
            buffer = ByteBuffer.allocate(100 * 1024);
            from = channel.receive(buffer);
            respond = from != null;
            if (new Date().getTime() - requestTime > 1000) {
                return false;
            }
        }
        LOGGER.debug("Reply was received");
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
            if (waitResponse(channel)) {
                Response response = SerializationUtils.deserialize(buffer.array());
                return response.message();
            } else throw new UnavailableServerException();
        } catch (IOException e) {
            return "Something went wrong: " + e.getMessage();
        }
    }

    /**
     * Gathers request from a line, opens channel, sends request and gets response from server
     *
     * @return message from response
     * @throws UnavailableServerException if server didn't reply
     */
    public String sendReqGetResp(String line) throws UnavailableServerException {
        Request request = new Request(line);
        return sendReqGetResp(request);
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
        return sendReqGetResp(request);
    }
}