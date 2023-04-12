package server;

import commands.CommandManager;
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
import java.util.NoSuchElementException;

/**
 * Does all works with clients interaction
 */
public class Connection {
    private static final Logger LOGGER = LogManager.getLogger(Connection.class);
    private final CommandManager manager;
    private DatagramSocket dataSock;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    private DatagramPacket dataPack;

    /**
     * Sets command manager, using for saving collection
     */
    public Connection(CommandManager manager) {
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
                if (!run())
                    break;
        } catch (IOException e) {
            LOGGER.error("Something went wrong =( " + e.getMessage());
        } catch (NoSuchElementException ignored) {
        } finally {
            dataSock.close();
        }
    }

    /**
     * Listening port.
     * If there are data package, unpacks request, processes it and sends response to the client
     */
    private boolean run() throws IOException {
        boolean exit = ServerCommand.execute(manager);
        if (exit)
            return false;
        if (readChannel()) {
            Request request = SerializationUtils.deserialize(buffer.array());
            LOGGER.info(String.format("Request command: %s, with args: %s",
                    request.getCommand(), request.getArg()));
            Response response = new Response(manager.seekCommand(request));
            sendResponse(response);
        }
        return true;
    }

    /**
     * Listens port and receives data packets from clients
     */
    private boolean readChannel() throws IOException {
        buffer = ByteBuffer.allocate(1024);
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
        int bytes = 65507; //Maximum weight of data in packet for UDP
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
