package server;

import commands.CommandManager;
import commands.ExitCommand;
import dragons.Dragon;
import network.CommandType;
import network.Request;
import network.Response;
import org.apache.commons.lang3.SerializationUtils;
import parsers.InputConsoleReader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Connection {
    private ByteBuffer buffer;
    private final int port;
    private final InetSocketAddress address;
    private final CommandManager manager;
    private final boolean working = true;

    public Connection(int port, CommandManager manager) {
        this.port = port;
        address = new InetSocketAddress("localhost", port);
        this.manager = manager;
    }

    public void open() {
        try (ServerSocketChannel server = ServerSocketChannel.open();
             Selector selector = Selector.open()) {
            server.bind(address);
            server.configureBlocking(false);
            server.register(selector, SelectionKey.OP_ACCEPT);
            System.out.printf("Server opened. Port: %s, Host: localhost\n", port);

            while (working)
                run(server, selector);

        } catch (IOException e) {
            System.out.println("Something went wrong =(");
        }
    }

    private void run(ServerSocketChannel server, Selector selector) throws IOException {
        if (System.in.available() > 0) {
            String line = InputConsoleReader.readNextLine();
        }
        if (working) process(server, selector);
    }

    private void process(ServerSocketChannel server, Selector selector) throws IOException {
        selector.selectNow();
        Set<SelectionKey> keys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = keys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            try {
                if (key.isValid()) {
                    if (key.isAcceptable()) acceptClient(key);
                    if (key.isReadable()) getReqSendResp(key);
                }
            } catch (SocketException e) {
                System.out.printf("Client %s unconnected\n",
                        ((SocketChannel) (key.channel())).getRemoteAddress().toString().substring(1));
                key.channel().close();
            }
            iterator.remove();
        }
    }

    private void acceptClient(SelectionKey key) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        SocketChannel client = server.accept();
        System.out.printf("Client %s was connected\n",
                client.getRemoteAddress().toString().substring(1));
        client.configureBlocking(false);
        client.register(key.selector(), SelectionKey.OP_READ);
    }

    private void getReqSendResp(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        Request request = getRequest(client);
        if (new String(buffer.array()).trim().isEmpty()) {
            System.out.printf("Client %s unconnected\n",
                    client.getRemoteAddress().toString().substring(1));
            client.close();
        } else {
            String out = manager.seekCommand(request);
            sendResponse(client, out);
        }
    }

    private Request getRequest(SocketChannel client) throws IOException {
        buffer = ByteBuffer.allocate(1024);
        client.read(buffer);
        return SerializationUtils.deserialize(buffer.array());
    }

    private void sendResponse(SocketChannel client, String output) throws IOException {
        Response response = new Response(output);
        buffer = ByteBuffer.wrap(SerializationUtils.serialize(response));
        client.write(buffer);
    }
}
