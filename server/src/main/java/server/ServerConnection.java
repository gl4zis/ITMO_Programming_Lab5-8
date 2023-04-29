package server;

import commands.CommandManager;
import commands.CommandValidator;
import general.OsUtilus;
import network.Request;
import network.Response;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Does all works with clients interaction
 */
public class ServerConnection {
    private static final Logger LOGGER = LogManager.getLogger(ServerConnection.class);
    private static final int MAX_UDP_BYTES_WINDOWS = 65507;
    private static final int MAX_UDP_BYTES_UNIX = 9216;
    private final CommandManager manager;
    private DatagramSocket dataSock;
    private ByteBuffer buffer;
    private DatagramPacket dataPack;

    /**
     * Sets command manager, using for saving collection
     */
    public ServerConnection(CommandManager manager) {
        this.manager = manager;
    }

    /**
     * Opens socket, configure it and starts listening port
     */
    public void open(int port) {
        try {
            dataSock = new DatagramSocket(port);
            dataSock.setSoTimeout(10);
            LOGGER.debug("Connection opened");
            while (true)
                run();
        } catch (IOException e) {
            LOGGER.error("Something went wrong =( " + e.getMessage());
        } finally {
            dataSock.close();
        }
    }

    /**
     * Listening port.
     * If there are data package, unpacks request, processes it and sends response to the client
     */
    private void run() throws IOException {
        ServerCommand.execute();
        if (readChannel()) {
            Request request = SerializationUtils.deserialize(buffer.array());
            LOGGER.info(String.format("Request command: %s, with args: %s",
                    request.command(), request.arg()));
            Response response;
            if (CommandValidator.validCommand(request))
                response = new Response(manager.seekCommand(request));
            else
                response = new Response("Incorrect request!!");
            sendResponse(response);
        }
    }

    /**
     * Listens port and receives data packets from clients
     */
    private boolean readChannel() throws IOException {
        buffer = ByteBuffer.allocate(2048);
        try {
            dataPack = new DatagramPacket(buffer.array(), buffer.capacity());
            dataSock.receive(dataPack);
            LOGGER.debug("Request received");
            return true;
        } catch (SocketTimeoutException e) {
            return false;
        }
    }

    /**
     * Sends response to the client
     */
    private void sendResponse(Response response) throws IOException {
        buffer = ByteBuffer.wrap(SerializationUtils.serialize(response));
        int bytes;
        if (OsUtilus.IsWindows()) bytes = MAX_UDP_BYTES_WINDOWS;
        else bytes = MAX_UDP_BYTES_UNIX;
        int packsNumber = buffer.capacity() / bytes + 1;
        for (int i = 0; i < packsNumber; i++) {
            byte[] partOfBuffer = Arrays.copyOfRange(buffer.array(), i * bytes, Math.min(buffer.capacity(), (i + 1) * bytes));
            sendDataPack(partOfBuffer);
            LOGGER.debug(String.format("Pack number %d, was sent (%d bytes)", i + 1, partOfBuffer.length));
            if (packsNumber > 3)
                wait(8);
        }
        LOGGER.info("Sent response (" + buffer.capacity() + " bytes) to the client: " +
                dataPack.getAddress().toString().substring(1));
    }

    /**
     * Sends one DatagramPacket to the client.
     * data.length must be <= 65507
     */
    private void sendDataPack(byte[] data) throws IOException {
        int port = dataPack.getPort();
        InetAddress host = dataPack.getAddress();
        dataPack = new DatagramPacket(data, data.length, host, port);
        dataSock.send(dataPack);
    }

    /**
     * Waiting for inputted number of milliseconds
     */
    private void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
