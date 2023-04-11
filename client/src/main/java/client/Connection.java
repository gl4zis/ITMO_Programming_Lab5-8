package client;

import exceptions.IncorrectInputException;
import exceptions.UnavailableServerException;
import network.Request;
import network.Response;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.InputConsoleReader;
import parsers.InputScriptReader;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;

public class Connection {
    private static final Logger LOGGER = LogManager.getLogger(Connection.class);
    private final InetSocketAddress address;
    private ByteBuffer buffer = ByteBuffer.allocate(100 * 1024);

    public Connection(InetAddress host, int port) {
        address = new InetSocketAddress(host, port);
    }

    public void run() {
        while (true) {
            System.out.print("-> ");
            String line = InputConsoleReader.readNextLine();
            String output = CommandProcessor.execute(this, line);
            if (output == null)
                break;
            if (!output.equals(""))
                System.out.println(output);
        }
    }

    private Request getRequest(String line, InputScriptReader reader) {
        try {
            if (reader == null) return new Request(line);
            else return new Request(line, reader);
        } catch (IncorrectInputException e) {
            return null;
        }
    }

    private void sendRequest(Request request, DatagramChannel channel) throws IOException {
        channel.configureBlocking(false);
        buffer = ByteBuffer.wrap(SerializationUtils.serialize(request));
        channel.send(buffer, address);
        LOGGER.debug("Request was sent");
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
                return false;
            }
        }
        LOGGER.debug("Reply was received");
        return true;
    }

    private String sendReqGetResp(Request request) throws UnavailableServerException {
        if (request != null) {
            try (DatagramChannel channel = DatagramChannel.open()) {
                sendRequest(request, channel);
                if (waitResponse(channel)) {
                    Response response = SerializationUtils.deserialize(buffer.array());
                    return response.message();
                } else throw new UnavailableServerException();
            } catch (IOException e) {
                return "Something went wrong: " + e.getMessage();
            }
        }
        throw new IncorrectInputException("Unknown command");
    }

    public String sendReqGetResp(String line) throws UnavailableServerException {
        Request request = getRequest(line, null);
        return sendReqGetResp(request);
    }

    public String sendReqGetResp(String line, InputScriptReader reader) throws UnavailableServerException {
        Request request = getRequest(line, reader);
        return sendReqGetResp(request);
    }
}