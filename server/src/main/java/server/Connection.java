package server;

import commands.CommandManager;
import network.Request;
import network.Response;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.InputConsoleReader;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

/**
 * Does all works with clients interaction
 */
public class Connection {
    private static final Logger LOGGER = LogManager.getLogger(Connection.class);
    private final CommandManager manager;
    private DatagramSocket dataSock;
    private int port;
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
        this.port = port;
        try {
            dataSock = new DatagramSocket(port);
            dataSock.setSoTimeout(10);
            LOGGER.debug("Connection opened");
            while (true)
                if (!run())
                    break;
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
    private boolean run() throws IOException {
        boolean exit = checkConsole();
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
     * Checks if admin typed something in console and processes it, executing server commands
     */
    private boolean checkConsole() throws IOException {
        if (System.in.available() > 0) {
            String line = InputConsoleReader.readNextLine();
            switch (line) {
                case "help" -> ServerCommand.help();
                case "exit" -> {
                    ServerCommand.save(manager);
                    return true;
                }
                case "save" -> ServerCommand.save(manager);
                default -> System.out.println("Unknown server command");
            }
        }
        return false;
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
        } catch (SocketTimeoutException ignored) {
            return false;
        }
    }

    /**
     * Sends response to the client
     */
    private void sendResponse(Response response) throws IOException {
        buffer = ByteBuffer.wrap(SerializationUtils.serialize(response));
        InetAddress host = dataPack.getAddress();
        port = dataPack.getPort();
        dataPack = new DatagramPacket(buffer.array(), buffer.capacity(), host, port);
        dataSock.send(dataPack);
        LOGGER.info("Sent response to the client: " + host.toString().substring(1));
    }
}
