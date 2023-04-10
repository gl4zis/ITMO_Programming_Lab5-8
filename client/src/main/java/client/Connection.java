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
    private final InetAddress host;
    private final int port;

    public Connection(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        SocketAddress address = new InetSocketAddress(host, port);
        ByteBuffer buffer = null;

        outerLoop:
        while (true) {
            String line = InputConsoleReader.readNextLine();
            Request request = getRequest(line);
            if (request != null) {
                try (DatagramChannel dataChan = DatagramChannel.open()) {
                    dataChan.configureBlocking(false);
                    SocketAddress from;
                    boolean respond = false;
                    buffer = ByteBuffer.wrap(SerializationUtils.serialize(request));
                    dataChan.send(buffer, address);
                    long requestTime = new Date().getTime();

                    while (!respond) {
                        buffer = ByteBuffer.allocate(100 * 1024);
                        from = dataChan.receive(buffer);
                        respond = from != null;
                        if (new Date().getTime() - requestTime > 1000) {
                            System.out.println("Server can't respond. Try request later");
                            continue outerLoop;
                        }
                    }
                    Response response = SerializationUtils.deserialize(buffer.array());
                    System.out.println(response.message());
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
}
