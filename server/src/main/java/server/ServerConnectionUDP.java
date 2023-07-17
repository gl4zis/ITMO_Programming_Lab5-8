package server;

import commands.CommandManager;
import commands.CommandType;
import commands.CommandValidator;
import exceptions.ExitException;
import general.OsUtilus;
import network.Request;
import network.RespCollection;
import network.Response;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.MyScanner;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * Does all works with clients interaction
 */
public class ServerConnectionUDP {
    private static final Logger LOGGER = LogManager.getLogger(ServerConnectionUDP.class);
    private static final int MAX_UDP_BYTES_WINDOWS = 65507;
    private static final int MAX_UDP_BYTES_UNIX = 9216;
    private final CommandManager manager;
    private final Queue<Pair<DatagramPacket, Request>> requests;
    private final Queue<Pair<DatagramPacket, Response>> responses;
    private DatagramSocket dataSock;

    /**
     * Constructor sets command manager for executing requests and creates queues for multithreading
     */
    ServerConnectionUDP(CommandManager manager) {
        this.manager = manager;
        requests = new ConcurrentLinkedDeque<>();
        responses = new ConcurrentLinkedDeque<>();
    }

    /**
     * Opens connection with clients on this port.
     * Creates DatagramSocket
     * Creates thread for checking console,
     * ForkJoinPool for reading requests,
     * CachedThreadPool for process requests
     * and starts method run
     *
     * @param port UDP port
     */
    public void open(int port) {
        try {
            Thread mainThread = Thread.currentThread();
            Thread console = new Thread(() -> consoleCheck(mainThread));
            console.setName("console");
            console.start();
            dataSock = new DatagramSocket(port);
            LOGGER.info("Connection opened. Port: " + dataSock.getLocalPort());
            run();
            console.interrupt();
        } catch (SocketException e) {
            LOGGER.error("Something went wrong ( " + e.getMessage());
        } finally {
            dataSock.close();
        }
    }

    /**
     * Manages multithreading.
     * While main thread is not interrupted creates threads for reading, processing and sending in loop.
     * As soon as main interrupts, shutdown all threads and exits from the app
     */
    private void run() {
        ForkJoinPool readPool = new ForkJoinPool(10);
        readPool.submit(() -> readBuffer(readPool));
        ExecutorService processPool = Executors.newCachedThreadPool();
        Thread mainThread = Thread.currentThread();
        while (!Thread.currentThread().isInterrupted()) {
            if (!requests.isEmpty())
                processPool.submit(() -> processRequest(mainThread));
            if (!responses.isEmpty())
                new Thread(this::sendReply).start();
        }
        readPool.shutdownNow();
        processPool.shutdownNow();
    }

    /**
     * Runnable method for reading buffer.
     * Works on recursion. As soon as the data coming in the buffer,
     * creates new thread with this method
     *
     * @param pool ForkJoinPool with reading threads
     */
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

    /**
     * Runnable method for processing requests.
     * If there are requests in queue 'requests',
     * gets it and executes desired command.
     * Interrupts main thread if it needs exit
     *
     * @param mainThread Thread 'main' for exiting
     */
    private void processRequest(Thread mainThread) {
        try {
            Pair<DatagramPacket, Request> pair = requests.poll();
            Request request = pair.getRight();
            Response response;
            if (CommandValidator.validCommand(request)) {
                if (request.command().equals(CommandType.SHOW)) {
                    response = new RespCollection(manager.getCollection());
                } else
                    response = new Response(manager.seekCommand(request));
            } else
                response = new Response("Incorrect request!!");
            DatagramPacket dataPack = pair.getLeft();
            responses.offer(new ImmutablePair<>(dataPack, response));
        } catch (ExitException e) {
            mainThread.interrupt();
        } catch (NullPointerException ignored) {
        } // Just terminate thread if requests is empty (other thread already got request)
    }

    /**
     * Runnable method for sends reply to the client.
     * Checks queue 'responses' and send if it's not empty
     */
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
                    break;
                } else {
                    LOGGER.info("Unknown server command");
                }
            }
        }
    }

    /**
     * Divides large packages into several small and sends it to the client
     *
     * @param buffer serialized response (can be very large)
     * @param host   host of client
     * @param port   port of client
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
