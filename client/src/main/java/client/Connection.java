package client;

import exceptions.EmptyInputException;
import exceptions.IncorrectInputException;
import network.Request;
import network.Response;
import org.apache.commons.lang3.SerializationUtils;
import parsers.InputConsoleReader;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;

public class Connection {
    private final InetSocketAddress address;
    private ByteBuffer buffer = ByteBuffer.allocate(100 * 1024);

    public Connection(InetAddress host, int port) {
        address = new InetSocketAddress(host, port);
    }

    public void run() {
        while (true) {
            System.out.print("-> ");
            String line = InputConsoleReader.readNextLine();
            if (line.equals("exit")) System.exit(0);
            Request request = getRequest(line);
            if (request != null) {
                try (DatagramChannel channel = DatagramChannel.open()) {
                    sendRequest(request, channel);
                    if (waitResponse(channel)) {
                        Response response = SerializationUtils.deserialize(buffer.array());
                        System.out.println(response.message());
                    }
                } catch (IOException e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
        }
    }

    private Request getRequest(String line) {
        Request request = null;
        try {
            request = new Request(line);
        } catch (EmptyInputException ignored) {
        } catch (IncorrectInputException e) {
            System.out.println(e.getMessage());
        }
        return request;
    }

    private void sendRequest(Request request, DatagramChannel channel) throws IOException {
        channel.configureBlocking(false);
        buffer = ByteBuffer.wrap(SerializationUtils.serialize(request));
        channel.send(buffer, address);
    }

    private boolean waitResponse(DatagramChannel channel) throws IOException {
        long requestTime = new Date().getTime();
        boolean respond = false;
        SocketAddress from;
        while (!respond) {
            buffer = ByteBuffer.allocate(100 * 1024);
            from = channel.receive(buffer);
            respond = from != null;
            if (new Date().getTime() - requestTime > 1000) {
                System.out.println("Server is unavailable. Try request later");
                return false;
            }
        }
        return true;
    }
}
