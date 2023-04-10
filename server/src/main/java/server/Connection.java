package server;

import commands.CommandManager;
import commands.SaveCommand;
import exceptions.IncorrectInputException;
import network.Request;
import network.Response;
import org.apache.commons.lang3.SerializationUtils;
import parsers.InputConsoleReader;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

public class Connection {

    private ByteBuffer buffer;
    private DatagramSocket dataSock;
    private DatagramPacket dataPack;
    private final CommandManager manager;
    private int port;

    public Connection(CommandManager manager) {
        this.manager = manager;
    }

    public void open(int port) {
        this.port = port;
        buffer = ByteBuffer.allocate(1024 * 1024);
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
            if (line.equals("save") || line.equals("exit")) {
                Request request = new Request("save");
                manager.seekCommand(request);
                if (line.equals("exit")) {
                    request = new Request("exit");
                    manager.seekCommand(request);
                }
            } else System.out.println("Unknown server command");
        }
        buffer = ByteBuffer.allocate(1024 * 1024);
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
