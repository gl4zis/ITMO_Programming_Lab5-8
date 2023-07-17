package server;

import commands.CommandManager;
import commands.CommandType;
import network.Request;
import network.RespCollection;
import network.Response;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.MyScanner;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerConnectionTCP {
    private final Logger LOGGER = LogManager.getLogger(ServerConnectionTCP.class);
    private final CommandManager manager;
    private volatile boolean isWorking;

    public ServerConnectionTCP(CommandManager manager) {
        this.manager = manager;
        isWorking = true;
    }

    public void open(int port) {
        try (ServerSocketChannel serv = ServerSocketChannel.open()) {
            InetAddress host = InetAddress.getByName("localhost");
            SocketAddress address = new InetSocketAddress(host, port);
            serv.bind(address);
            LOGGER.info("SERVER was started on host " + host.getHostAddress() + " and port " + port);
            Thread mainThread = Thread.currentThread();
            Thread console = new Thread(() -> consoleCheck(mainThread));
            console.start();
            run(serv);
            console.interrupt();
        } catch (UnknownHostException ignored) { //Localhost is known always
        } catch (IOException e) {
            if (e.getMessage() != null)
                LOGGER.error(e.getMessage());
        }
    }

    public void run(ServerSocketChannel server) throws IOException {
        ExecutorService pool = Executors.newCachedThreadPool();
        while (!Thread.currentThread().isInterrupted()) {
            SocketChannel client = server.accept();
            pool.submit(() -> clientWork(client));
        }
        pool.shutdown();
    }

    public void clientWork(SocketChannel client) {
        while (isWorking && client.isConnected()) {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(2048);
                client.read(buffer);
                Request request = SerializationUtils.deserialize(buffer.array());
                LOGGER.info("Request was accepted. Command " + request.command().getName() + " with args " + request.arg());
                Response response;
                if (request.command() == CommandType.SHOW)
                    response = new RespCollection(manager.getCollection());
                else
                    response = new Response(manager.seekCommand(request));
                buffer = ByteBuffer.wrap(SerializationUtils.serialize(response));
                LOGGER.info("Response was sent (" + buffer.array().length + " bytes)");
                client.write(buffer);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                break;
            }
        }
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runnable method for checking console.
     * If there are 'exit' command interrupts main thread for exiting from the app
     *
     * @param mainThread Thread main for interrupting
     */
    private void consoleCheck(Thread mainThread) {
        MyScanner console = new MyScanner(System.in);
        while (!Thread.currentThread().isInterrupted()) {
            String line = console.checkConsole();
            if (line != null && !line.equals("")) {
                if ("exit".equals(line)) {
                    mainThread.interrupt();
                    isWorking = false;
                    break;
                } else {
                    LOGGER.info("Unknown server command");
                }
            }
        }
    }
}
