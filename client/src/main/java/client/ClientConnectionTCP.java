package client;

import commands.CommandType;
import dragons.DragonCollection;
import exceptions.UnavailableServerException;
import network.Request;
import network.RespCollection;
import network.Response;
import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class ClientConnectionTCP {
    private static final Logger LOGGER = LogManager.getLogger(ClientConnectionTCP.class);
    private final SocketAddress serverAddr;
    private SocketChannel socket;

    public ClientConnectionTCP(InetAddress host, int port) {
        serverAddr = new InetSocketAddress(host, port);
        Thread connecting = new Thread(this::connect);
        connecting.setName("connecting");
        connecting.start();
    }

    private synchronized void connect() {
        while (socket == null) {
            try {
                SocketChannel socket = SocketChannel.open();
                socket.connect(serverAddr);
                socket.configureBlocking(false);
                this.socket = socket;
            } catch (IOException ignored) {
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void reconnect() {
        boolean isAlreadyConnecting = false;
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread.getName().equals("connecting")) {
                isAlreadyConnecting = true;
                break;
            }
        }
        if (!isAlreadyConnecting) {
            Thread connecting = new Thread(this::connect);
            connecting.setName("connecting");
            connecting.start();
        }
    }

    private Response sendReqGetResp(Request request) throws UnavailableServerException {
        if (socket == null)
            throw new UnavailableServerException("No connection");
        ByteBuffer buffer = ByteBuffer.wrap(SerializationUtils.serialize(request));
        try {
            socket.write(buffer);
            LOGGER.info("Request '" + request.command().getName() + "' was sent to " + socket.getRemoteAddress());
            return readResp();
        } catch (IOException e) {
            throw new UnavailableServerException(e.getMessage());
        }
    }

    private Response readResp() throws IOException, UnavailableServerException {
        long start = new Date().getTime();
        while (new Date().getTime() - start < 1000) {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(1024000);
                socket.read(buffer);
                return SerializationUtils.deserialize(buffer.array());
            } catch (ClassCastException | SerializationException ignored) {
            }
        }
        throw new UnavailableServerException("No connection");
    }

    public DragonCollection getCollection() throws UnavailableServerException {
        Request request = new Request(CommandType.SHOW, null, null, null);
        return ((RespCollection) sendReqGetResp(request)).collection;
    }

    public String getReply(Request request) throws UnavailableServerException {
        return sendReqGetResp(request).message;
    }

}
