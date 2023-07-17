package client;

import commands.CommandType;
import dragons.DragonCollection;
import exceptions.UnavailableServerException;
import general.OsUtilus;
import network.Request;
import network.RespCollection;
import network.Response;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
public class ClientConnectionUDP {
    private static final Logger LOGGER = LogManager.getLogger(ClientConnectionUDP.class);
    private static final int MAX_UDP_BYTES_WINDOWS = 65507;
    private static final int MAX_UDP_BYTES_UNIX = 9216;
    private final InetSocketAddress address;
    private ByteBuffer buffer = ByteBuffer.allocate(100 * 1024);

    /**
     * Standard constructor
     *
     * @param host localhost
     * @param port server port
     */
    public ClientConnectionUDP(InetAddress host, int port) {
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
    private Response sendReqGetResp(Request request) throws UnavailableServerException {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            sendRequest(request, channel);
            if (receivePacks(channel)) {
                return SerializationUtils.deserialize(buffer.array());
            } else {
                throw new UnavailableServerException("No connection");
            }
        } catch (IOException e) {
            LOGGER.error("Something went wrong: " + e.getMessage());
            throw new UnavailableServerException("No connection");
        }
    }

    public String getReply(Request request) throws UnavailableServerException {
        return sendReqGetResp(request).message;
    }

    public DragonCollection getCollection() throws UnavailableServerException {
        Request request = new Request(CommandType.SHOW, null, null, null);
        return ((RespCollection) sendReqGetResp(request)).collection;
    }
}