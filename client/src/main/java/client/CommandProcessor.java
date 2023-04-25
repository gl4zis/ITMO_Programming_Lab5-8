package client;

import exceptions.ExitException;
import exceptions.IncorrectDataException;
import exceptions.IncorrectInputException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.MyScanner;
import parsers.ScriptParser;
import parsers.StringModificator;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashSet;

/**
 * Preprocessing commands on client
 */
public abstract class CommandProcessor {
    private static final Logger LOGGER = LogManager.getLogger(CommandProcessor.class);
    private static final MyScanner CONSOLE = new MyScanner(System.in);

    /**
     * Processes string line. Finds there special commands, processes it, sends requests and return response
     *
     * @param conn by this, realizes requests and responses
     * @param line there you want to find command
     * @return responded message from the server
     */
    public static String execute(Connection conn, String line) {
        if (line.trim().isEmpty())
            return "";
        String output = execute(conn, line, CONSOLE);
        if (output == null) throw new ExitException();
        else return output;
    }

    /**
     * Similarly execute(Connection, String), but can execute commands from script
     */
    private static String execute(Connection conn, String line, MyScanner reader) {
        try {
            return switch (line.split(" ")[0]) {
                case "exit" -> throw new ExitException();
                case "help" -> help(conn);
                case "update" -> update(conn, line, reader);
                case "ping" -> ping(conn);
                case "execute_script" -> ex_script(conn, line);
                default -> conn.sendReqGetResp(line, reader);
            };
        } catch (IncorrectInputException e) {
            return e.getMessage();
        }
    }

    /**
     * Special client command 'ping'
     * Checks connection to server
     */
    private static String ping(Connection conn) {
        long startTime = new Date().getTime();
        String output = "";
        for (int i = 0; i < 10; i++) {
            output = conn.sendReqGetResp("ping", CONSOLE);
        }
        long endTime = new Date().getTime() - startTime;
        return output + "\nAverage reply time: " + endTime / 10 + " ms";
    }

    /**
     * Special client command 'help'
     */
    private static String help(Connection conn) {
        String output = conn.sendReqGetResp("help", CONSOLE);
        output += """
                        
                \texit : terminate the program
                \texecute_script filepath : execute script in the file, by its filepath""";
        return output;
    }

    /**
     * Special client command 'update'
     */
    private static String update(Connection conn, String line, MyScanner reader) {
        String find = "find" + line.substring(6);
        String output = conn.sendReqGetResp(find, CONSOLE);
        if (!output.startsWith("No such")) {
            output = conn.sendReqGetResp(line, reader);
        }
        return output;
    }

    /**
     * Special client command 'execute_script'
     */
    private static String ex_script(Connection conn, String line) {
        validateScript(conn, new HashSet<>(), line);
        return "";
    }

    /**
     * Executes command from reader
     */
    private static void ex_script(Connection conn, HashSet<String> files, MyScanner reader) {
        String line = reader.nextLine();
        while (line != null) {
            if (!line.trim().isEmpty()) {
                try {
                    if (line.startsWith("execute_script")) {
                        validateScript(conn, files, line);
                    } else {
                        String output = execute(conn, line, reader);
                        LOGGER.info("Executing: " + line);
                        LOGGER.info(output);
                    }
                } catch (IncorrectInputException ignored) {
                } catch (IncorrectDataException e) {
                    LOGGER.warn(e.getMessage());
                }
            }
            line = reader.nextLine();
        }
    }

    /**
     * Validates script file, checks recursion etc
     */
    private static void validateScript(Connection conn, HashSet<String> files, String line) {
        String filePath;
        MyScanner reader;
        try {
            filePath = StringModificator.filePathFormat(line.split(" ")[1]);
            if (!files.contains(filePath)) {
                files.add(filePath);
                reader = new MyScanner(ScriptParser.readLines(filePath));
                LOGGER.info("Executing script: " + filePath);
            } else {
                LOGGER.warn("Try of recursion! Script wouldn't execute");
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IncorrectInputException("Unknown command");
        } catch (FileNotFoundException | SecurityException e) {
            throw new IncorrectInputException("File don't exist or there are no enough rights");
        }

        ex_script(conn, files, reader);

        files.remove(filePath);
        LOGGER.info(String.format("Script: %s executing ended", filePath));
    }
}