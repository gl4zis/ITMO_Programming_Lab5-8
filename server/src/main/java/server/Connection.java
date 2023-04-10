package server;

import commands.CommandManager;
import network.Request;
import network.Response;
import org.apache.commons.lang3.SerializationUtils;
import parsers.InputConsoleReader;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

public class Connection {
    private final CommandManager manager;
    private DatagramSocket dataSock;
    private int port;

    public Connection(CommandManager manager) {
        this.manager = manager;
    }

    public void open(int port) {
        this.port = port;
        try {
            dataSock = new DatagramSocket(port);
            dataSock.setSoTimeout(100);
            while (true)
                run();
        } catch (IOException e) {
            System.out.println("Something went wrong =( " + e.getMessage());
        } finally {
            dataSock.close();
        }
    }

    private void run() throws IOException {
        if (System.in.available() > 0) {
            String line = InputConsoleReader.readNextLine();
            switch (line) {
                case "help" -> ServerCommand.help();
                case "exit" -> ServerCommand.exit(manager);
                case "save" -> ServerCommand.save(manager);
                default -> System.out.println("Unknown server command");
            }
        }
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        DatagramPacket dataPack;
        try {
            dataPack = new DatagramPacket(buffer.array(), buffer.capacity());
            dataSock.receive(dataPack);
        } catch (SocketTimeoutException ignored) {
            return;
        }
        Request request = SerializationUtils.deserialize(buffer.array());
        System.out.printf("Request command: %s, with args: %s\n%s\n",
                request.getCommand(), request.getArg(), request.getDragon());
        Response response = new Response(manager.seekCommand(request));
        buffer = ByteBuffer.wrap(SerializationUtils.serialize(response));
        InetAddress host = dataPack.getAddress();
        port = dataPack.getPort();
        dataPack = new DatagramPacket(buffer.array(), buffer.capacity(), host, port);
        dataSock.send(dataPack);
        System.out.println("Sent response to the client: " + host.toString().substring(1));
    }
}
