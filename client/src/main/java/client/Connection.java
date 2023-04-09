package client;

import exceptions.EmptyInputException;
import exceptions.IncorrectInputException;
import network.Response;
import org.apache.commons.lang3.SerializationUtils;
import parsers.InputConsoleReader;
import network.Request;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Connection {
    private final int port;
    private boolean message = false;

    public Connection(int port) {
        this.port = port;
    }

    public void run() {
        try (SocketChannel client = SocketChannel.open(new InetSocketAddress("localhost", port))) {
            message = false;
            System.out.printf("Connected to server. Port: %s, Host: %s\n", port, InetAddress.getLocalHost());
            while (true) {
                System.out.print("-> ");
                String line = InputConsoleReader.readNextLine();
                if (!client.isConnected()) break;
                Request request = getRequest(line);
                if (request != null) {
                    ByteBuffer buffer = ByteBuffer.wrap(SerializationUtils.serialize(request));
                    client.write(buffer);
                    buffer = ByteBuffer.allocate(1048576);
                    client.read(buffer);
                    Response response = SerializationUtils.deserialize(buffer.array());
                    System.out.print(response.message());
                }
            }
        } catch (IOException e) {
            if (!message) {
                System.out.println("Can't connect to server");
                System.out.println("Waiting for server");
                message = true;
            }
        }
    }

    public Request getRequest(String line) {
        Request request = null;
        try {
            request = new Request(line);
        } catch (EmptyInputException ignored) {
        } catch (IncorrectInputException e) {
            System.out.println(e.getMessage());
        }
        return request;
    }
}
