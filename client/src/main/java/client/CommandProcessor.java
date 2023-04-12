package client;

import exceptions.IncorrectDataException;
import exceptions.IncorrectInputException;
import exceptions.UnavailableServerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.InputScriptReader;
import parsers.StringModificator;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashSet;

/**
 * Preprocessing commands on client
 */
public abstract class CommandProcessor {
    private static final Logger LOGGER = LogManager.getLogger(CommandProcessor.class);

    /**
     * Processes string line. Finds there special commands, processes it, sends requests and return response
     *
     * @param conn by this, realizes requests and responses
     * @param line there you want to find command
     * @return responded message from the server
     */
    public static String execute(Connection conn, String line) throws UnavailableServerException {
        if (line.trim().isEmpty())
            return "";
        return execute(conn, line, null);
    }

    /**
     * Similarly execute(Connection, String), but can execute commands from script
     */
    private static String execute(Connection conn, String line, InputScriptReader reader) throws UnavailableServerException {
        try {
            if (!ping(conn, 1).equals("No connection")) {
                switch (line.split(" ")[0]) {
                    case "exit":
                        return null;
                    case "help":
                        return help(conn);
                    case "update":
                        return update(conn, line, reader);
                    case "ping":
                        return ping(conn, 10);
                    case "execute_script":
                        if (ex_script(conn, line))
                            return null;
                        return "";
                    default:
                        return conn.sendReqGetResp(line, reader);
                }
            } else
                throw new UnavailableServerException("Repeat command, when server will start reply\n(...Trying connect...)");
        } catch (IncorrectInputException e) {
            return e.getMessage();
        }
    }

    /**
     * Special client command 'ping'
     * Checks connection to server
     */
    private static String ping(Connection conn, int reqNumber) {
        try {
            long startTime = new Date().getTime();
            String output = "";
            for (int i = 0; i < reqNumber; i++) {
                output = conn.sendReqGetResp("ping");
            }
            long endTime = new Date().getTime() - startTime;
            return output + "\nAverage reply time: " + endTime / reqNumber + " ms";
        } catch (UnavailableServerException e) {
            return "No connection";
        }
    }

    /**
     * Special client command 'help'
     */
    private static String help(Connection conn) throws UnavailableServerException {
        String output = conn.sendReqGetResp("help");
        output += """
                        
                \texit : terminate the program
                \texecute_script filepath : execute script in the file, by its filepath""";
        return output;
    }

    /**
     * Special client command 'update'
     */
    private static String update(Connection conn, String line, InputScriptReader reader) throws UnavailableServerException {
        String find = "find" + line.substring(6);
        String output = conn.sendReqGetResp(find);
        if (!output.startsWith("No such")) {
            return conn.sendReqGetResp(line, reader);
        } else return output;
    }

    /**
     * Special client command 'execute_script'
     */
    private static boolean ex_script(Connection conn, String line) throws UnavailableServerException {
        return validateScript(conn, new HashSet<>(), line);
    }

    /**
     * Executes command from reader
     */
    private static boolean ex_script(Connection conn, HashSet<String> files, InputScriptReader reader)
            throws UnavailableServerException {
        String line = reader.readNextLine();
        while (line != null) {
            if (!line.trim().isEmpty()) {
                try {
                    if (line.startsWith("execute_script")) {
                        validateScript(conn, files, line);
                    } else {
                        String output = execute(conn, line, reader);
                        LOGGER.info("Executing: " + line);
                        if (output == null)
                            return false;
                        LOGGER.info(output);
                    }
                } catch (IncorrectInputException ignored) {
                } catch (IncorrectDataException e) {
                    LOGGER.warn(e.getMessage());
                }
            }
            line = reader.readNextLine();
        }
        return true;
    }

    /**
     * Validates script file, checks recursion etc
     */
    private static boolean validateScript(Connection conn, HashSet<String> files, String line) throws UnavailableServerException {
        String filePath;
        InputScriptReader reader;
        try {
            filePath = StringModificator.filePathFormat(line.split(" ")[1]);
            if (!files.contains(filePath)) {
                files.add(filePath);
                reader = new InputScriptReader(filePath);
                LOGGER.info("Executing script: " + filePath);
            } else {
                LOGGER.warn("Try of recursion! Script wouldn't execute");
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IncorrectInputException("Unknown command");
        } catch (FileNotFoundException | SecurityException e) {
            throw new IncorrectInputException("File don't exist or there are no enough rights");
        }

        boolean exit = !ex_script(conn, files, reader);

        files.remove(filePath);
        LOGGER.info(String.format("Script: %s executing ended", filePath));
        return exit;
    }
}
