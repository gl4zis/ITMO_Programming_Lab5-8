package server;

import commands.CommandManager;
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
import parsers.MyScanner;

import javax.xml.crypto.Data;
import java.awt.desktop.PreferencesEvent;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Queue;
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
        new Thread(this::consoleCheck).start();
        try {
            dataSock = new DatagramSocket(port);
            dataSock.setSoTimeout(10);
            LOGGER.debug("Connection opened");
            ForkJoinPool pool1 = new ForkJoinPool();
            ExecutorService pool2 = Executors.newCachedThreadPool();
            while (true)
                run(pool1, pool2);
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
    private void run(ForkJoinPool pool1, ExecutorService pool2) {
        pool1.submit(this::readBuffer);
        pool2.submit(this::processRequest);
        new Thread(this::sendReply).start();
    }

    private void readBuffer() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(2048);
            DatagramPacket dataPack = new DatagramPacket(buffer.array(), buffer.capacity());
            dataSock.receive(dataPack);
            LOGGER.debug("Request received");
            Request request = SerializationUtils.deserialize(buffer.array());
            LOGGER.info(String.format("Request command: %s, with args: %s",
                    request.command(), request.arg()));
            requests.offer(new ImmutablePair<>(dataPack, request));
        } catch (SocketTimeoutException ignored) {
        } catch (IOException e) {
            LOGGER.error("Something went wrong ( " + e.getMessage());
        }
    }

    private void processRequest() {
        if (!requests.isEmpty()) {
            Pair<DatagramPacket, Request> pair = requests.poll();
            Request request = pair.getRight();
            Response response;
            if (CommandValidator.validCommand(request))
                response = new Response(manager.seekCommand(request));
            else
                response = new Response("Incorrect request!!");
            DatagramPacket dataPack = pair.getLeft();
            responses.offer(new ImmutablePair<>(dataPack, response));
        }
    }

    private void sendReply() {
        if (!responses.isEmpty()) {
            Pair<DatagramPacket, Response> pair = responses.poll();
            Response response = pair.getRight();
            ByteBuffer buffer = ByteBuffer.wrap(SerializationUtils.serialize(response));
            DatagramPacket dataPack = pair.getLeft();
            sendResponse(buffer, dataPack.getAddress(), dataPack.getPort());
        }
    }

    private void consoleCheck() {
        while (true) {
            String line = new MyScanner(System.in).nextLine();
            if (line != null) {
                if ("exit".equals(line)) {
                    System.exit(0);
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
