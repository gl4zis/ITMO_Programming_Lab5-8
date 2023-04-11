import exceptions.EmptyInputException;
import exceptions.IncorrectDataException;
import exceptions.IncorrectInputException;
import network.CommandType;
import network.Request;
import network.Response;
import org.apache.commons.lang3.SerializationUtils;
import parsers.InputConsoleReader;
import parsers.InputScriptReader;
import parsers.StringModificator;

import javax.lang.model.element.ElementKind;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

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
            try (DatagramChannel channel = DatagramChannel.open()) {
                CommandProcessor proc = new CommandProcessor(channel);
                proc.execute(line, null);
            } catch (IOException e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
    }

    private Request getRequest(String line, InputScriptReader reader) {
        Request request = null;
        try {
            if (reader == null)
                request = new Request(line);
            else request = new Request(line, reader);
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

    class CommandProcessor {
        private final DatagramChannel channel;

        private CommandProcessor(DatagramChannel channel) {
            this.channel = channel;
        }

        private void execute(String line, InputScriptReader reader) throws IOException {
            switch (line.split(" ")[0]) {
                case "exit" -> exit();
                case "help" -> help(line);
                case "update" -> update(line, reader);
                case "execute_script" -> ex_script(line);
                default -> {
                    Request request = getRequest(line, reader);
                    if (request != null) {
                        sendRequest(request, channel);
                        if (waitResponse(channel)) {
                            Response response = SerializationUtils.deserialize(buffer.array());
                            System.out.println(response.message());
                        }
                    }
                }
            }
        }

        private void help(String line) throws IOException {
            Request request = getRequest(line, null);
            sendRequest(request, channel);
            if (waitResponse(channel)) {
                Response response = SerializationUtils.deserialize(buffer.array());
                String output = response.message();
                output += """

                        \texit : terminate the program
                        \texecute_script filepath : execute script in the file, by its filepath""";
                System.out.println(output);
            }
        }

        private void exit() {
            System.out.println("Bye bye");
            System.exit(0);
        }

        private void update(String line, InputScriptReader reader) throws IOException {
            String find = "find" + line.substring(6);
            Request findRequest = getRequest(find, null);
            if (findRequest != null) {
                sendRequest(findRequest, channel);
                if (waitResponse(channel)) {
                    Response response = SerializationUtils.deserialize(buffer.array());
                    if (!response.message().startsWith("No such")) {
                        Request updateRequest = getRequest(line, reader);
                        sendRequest(updateRequest, channel);
                        if (waitResponse(channel)) {
                            response = SerializationUtils.deserialize(buffer.array());
                            System.out.println(response.message());
                        }
                    } else System.out.println(response.message());
                }
            }
        }

        private void ex_script(String line) throws IOException {
            ex_script(new HashSet<>(), line);
        }

        private void ex_script(HashSet<String> files, String line) throws IOException {
            String filePath;
            try {
                filePath = StringModificator.filePathFormat(line.split(" ")[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Incorrect data inputted: Unknown command");
                return;
            }

            if (!files.contains(filePath)) {
                files.add(filePath);
                try {
                    InputScriptReader reader = new InputScriptReader(filePath);
                    line = reader.readNextLine();
                    while (line != null) {
                        System.out.println("\nExecuting: " + line);
                        if (line.startsWith("execute_script")) ex_script(files, line);
                        else execute(line, reader);
                        line = reader.readNextLine();
                    }
                    System.out.println("End of executing script");
                } catch (FileNotFoundException | SecurityException e) {
                    System.out.println("File didn't find or there is no access to file");
                }
                files.remove(filePath);
            } else System.out.println("Try of recursion! Script wouldn't execute");
        }
    }
}