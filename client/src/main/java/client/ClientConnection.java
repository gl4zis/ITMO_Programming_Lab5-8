package client;

import commands.CommandType;
import commands.CommandValidator;
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
import user.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * Realization of connection to the server.
 * Requests, responses etc
 */
public class ClientConnection {
    private static final Logger LOGGER = LogManager.getLogger(ClientConnection.class);
    private static final int MAX_UDP_BYTES_WINDOWS = 65507;
    private static final int MAX_UDP_BYTES_UNIX = 9216;
    private final InetSocketAddress address;
    public boolean connected = true;
    private ByteBuffer buffer = ByteBuffer.allocate(100 * 1024);

    /**
     * Standard constructor
     *
     * @param host localhost
     * @param port server port
     */
    public ClientConnection(InetAddress host, int port) {
        address = new InetSocketAddress(host, port);
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
        int packNumber = 1;
        byte[] replyArr = null;
        int bytes;
        if (OsUtilus.IsWindows()) bytes = MAX_UDP_BYTES_WINDOWS;
        else bytes = MAX_UDP_BYTES_UNIX;
        do {
            ByteBuffer partOfBuffer = ByteBuffer.allocate(bytes);
            if (!waitResponse(channel, partOfBuffer))
                return false;
            LOGGER.debug(String.format("Pack number %d, was received (%d bytes)", packNumber++, partOfBuffer.capacity()));
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
            if (new Date().getTime() - requestTime > 500) {
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
    public String sendReqGetResp(String line, MyScanner reader, User user) {
        String output;
        Request request = CommandValidator.validCommand(line, reader, user);
        while (true) {
            try {
                output = sendReqGetResp(request);
                break;
            } catch (UnavailableServerException ignored) {
            }
        }
        return output;
    }

    public Object tryConnecting(Callable<Object> call) {
        Object result = null;
        try {
            result = call.call();
        } catch (Exception ignored) {
        }
        connected = result != null;
        return result;
    }

    public boolean signIn(User user) {
        Request request = new Request(CommandType.SIGN_IN, null, null, user);
        Object result = tryConnecting(() -> sendReqGetResp(request));
        if (result == null)
            return false;
        else
            return ((String) result).startsWith("User was");
    }

    public boolean signUp(User user) {
        Request request = new Request(CommandType.SIGN_UP, null, null, user);
        Object result = tryConnecting(() -> sendReqGetResp(request));
        if (result == null)
            return false;
        else
            return ((String) result).startsWith("User was");
    }

    public boolean changePasswd(User user, String newPasswd) {
        Request request = new Request(CommandType.CHANGE_PASSWORD, User.hashPasswd(newPasswd, 500), null, user);
        Object result = tryConnecting(() -> sendReqGetResp(request));
        if (result == null)
            return false;
        else
            return ((String) result).startsWith("Password was");
    }
}