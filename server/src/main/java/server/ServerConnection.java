package server;

import commands.CommandManager;
import commands.CommandType;
import commands.CommandValidator;
import exceptions.ExitException;
import general.OsUtilus;
import network.Request;
import network.Response;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.arbiters.DefaultArbiter;
import org.junit.experimental.theories.Theories;
import parsers.MyScanner;

import javax.xml.crypto.Data;
import java.awt.desktop.PreferencesEvent;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * Does all works with clients interaction
 */
public class ServerConnection {
    private static final Logger LOGGER = LogManager.getLogger(ServerConnection.class);
    private static final int MAX_UDP_BYTES_WINDOWS = 65507;
    private static final int MAX_UDP_BYTES_UNIX = 9216;
    private final CommandManager manager;
    private final Queue<Pair<DatagramPacket, Request>> requests;
    private final Queue<Pair<DatagramPacket, Response>> responses;
    private DatagramSocket dataSock;

    /**
     * Sets command manager, using for saving collection
     */
    public ServerConnection(CommandManager manager) {
        this.manager = manager;
        requests = new ConcurrentLinkedDeque<>();
        responses = new ConcurrentLinkedDeque<>();
    }

    /**
     * Opens socket, configure it and starts listening port
     */
    public void open(int port) {
        try {
            Thread mainThread = Thread.currentThread();
            Thread console = new Thread(() -> consoleCheck(mainThread));
            console.setName("console");
            console.start();
            dataSock = new DatagramSocket(port);
            LOGGER.debug("Connection opened");
            ForkJoinPool readPool = new ForkJoinPool();
            readPool.submit(() -> readBuffer(readPool));
            ExecutorService processPool = Executors.newCachedThreadPool();
            run(processPool);
            readPool.shutdownNow();
        } catch (SocketException e) {
            LOGGER.error("Something went wrong ( " + e.getMessage());
        } finally {
            dataSock.close();
        }
    }

    /**
     * Listening port.
     * If there are data package, unpacks request, processes it and sends response to the client
     */
    private void run(ExecutorService processPool) {
        while (!Thread.currentThread().isInterrupted()) {
            if (!requests.isEmpty())
                processPool.submit(this::processRequest);
            if (!responses.isEmpty())
                new Thread(this::sendReply).start();
        }
        processPool.shutdownNow();
    }

    private void readBuffer(ForkJoinPool pool) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(2048);
            DatagramPacket dataPack = new DatagramPacket(buffer.array(), buffer.capacity());
            dataSock.receive(dataPack);
            pool.submit(() -> readBuffer(pool));
            LOGGER.debug("Request received");
            Request request = SerializationUtils.deserialize(buffer.array());
            LOGGER.info(String.format("Request command: %s, with args: %s",
                    request.command(), request.arg()));
            requests.offer(new ImmutablePair<>(dataPack, request));
        } catch (IOException ignored) {
        }
    }

    private void processRequest() {
        try {
            Pair<DatagramPacket, Request> pair = requests.poll();
            Request request = pair.getRight();
            Response response;
            if (CommandValidator.validCommand(request))
                response = new Response(manager.seekCommand(request));
            else
                response = new Response("Incorrect request!!");
            DatagramPacket dataPack = pair.getLeft();
            responses.offer(new ImmutablePair<>(dataPack, response));
        } catch (NullPointerException ignored) {
        } // Just terminate thread if requests is empty (other thread already got request)
    }

    private void sendReply() {
        try {
            Pair<DatagramPacket, Response> pair = responses.poll();
            Response response = pair.getRight();
            ByteBuffer buffer = ByteBuffer.wrap(SerializationUtils.serialize(response));
            DatagramPacket dataPack = pair.getLeft();
            sendResponse(buffer, dataPack.getAddress(), dataPack.getPort());
        } catch (NullPointerException ignored) {
        } // Just terminate thread if responses is empty (other thread already got response)
    }

    private void consoleCheck(Thread mainThread) {
        MyScanner console = new MyScanner(System.in);
        while (true) {
            String line = console.nextLine();
            if (line != null && !line.equals("")) {
                if ("exit".equals(line)) {
                    mainThread.interrupt();
                    break;
                } else {
                    LOGGER.info("Unknown server command");
                }
            }
        }
    }

    /**
     * Sends response to the client
     */
    private void sendResponse(ByteBuffer buffer, InetAddress host, int port) {
        int bytes;
        if (OsUtilus.IsWindows()) bytes = MAX_UDP_BYTES_WINDOWS;
        else bytes = MAX_UDP_BYTES_UNIX;
        int packsNumber = buffer.capacity() / bytes + 1;

        try {
            for (int i = 0; i < packsNumber; i++) {
                byte[] partOfBuffer = Arrays.copyOfRange(buffer.array(), i * bytes, Math.min(buffer.capacity(), (i + 1) * bytes));
                DatagramPacket dataPack = new DatagramPacket(partOfBuffer, partOfBuffer.length, host, port);
                new DatagramSocket().send(dataPack);

                LOGGER.debug(String.format("Pack number %d, was sent (%d bytes)", i + 1, partOfBuffer.length));
                if (packsNumber > 3)
                    wait(8);
            }
            LOGGER.info("Sent response (" + buffer.capacity() + " bytes) to the client: " +
                    host.toString().substring(1));
        } catch (IOException e) {
            LOGGER.error("Something went wrong ( " + e.getMessage());
        }
    }

    /**
     * Waiting for inputted number of nanoseconds
     */
    private void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
