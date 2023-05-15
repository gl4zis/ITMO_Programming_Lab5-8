package commands;

import client.ClientConnection;
import exceptions.IncorrectDataException;
import exceptions.IncorrectInputException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.MyScanner;
import parsers.ScriptParser;
import parsers.StringModificator;

import java.io.FileNotFoundException;
import java.util.HashSet;

/**
 * Command 'execute_script filepath'
 */
public class ExecuteScriptCommand extends Command {

    private static final Logger LOGGER = LogManager.getLogger(ExecuteScriptCommand.class);

    ExecuteScriptCommand(ClientConnection connection) {
        super(connection, "execute_script");
    }

    /**
     * Processes script file and sends every command from it one by one on server
     *
     * @return empty line
     */
    @Override
    public String execute(String line, MyScanner reader) {
        validateScript(new HashSet<>(), line);
        return "";
    }

    /**
     * Validates script (checks if file exists, checks recursion etc)
     *
     * @param files set with filePaths for checking recursion
     * @param line  new filepath
     */
    private void validateScript(HashSet<String> files, String line) {
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

        ex_script(files, reader);

        files.remove(filePath);
        LOGGER.info(String.format("Script: %s executing ended", filePath));
    }

    /**
     * Reads and executes command line by line from reader
     */
    private void ex_script(HashSet<String> files, MyScanner reader) {
        String line = reader.nextLine();
        while (line != null) {
            try {
                if (line.startsWith("execute_script")) {
                    validateScript(files, line);
                } else {
                    LOGGER.info("Executing: " + line);
                    System.out.println(conn.getProcessor().execute(line, reader));
                }
            } catch (IncorrectDataException e) {
                LOGGER.warn(e.getMessage());
            }
            line = reader.nextLine();
        }
    }
}
