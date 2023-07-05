package commands;

import GUI.MyConsole;
import exceptions.IncorrectDataException;
import exceptions.IncorrectInputException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.MyScanner;
import parsers.ScriptParser;
import settings.Settings;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;

/**
 * Command 'execute_script filepath'
 */
public class ExecuteScriptCommand extends Command {

    private static final Logger LOGGER = LogManager.getLogger(ExecuteScriptCommand.class);

    ExecuteScriptCommand(Settings settings) {
        super(settings, "execute_script");
    }

    /**
     * Processes script file and sends every command from it one by one on server
     */
    @Override
    public String execute() {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
        chooser.showOpenDialog(settings.getMainWindow());
        File script = chooser.getSelectedFile();
        String filePath = script.getPath();
        validateScript(new HashSet<>(), filePath);
        return "";
    }

    @Override
    public String exFromScript(MyScanner script, String line) {
        return null;
    }

    /**
     * Validates script (checks if file exists, checks recursion etc.)
     *
     * @param files    set with filePaths for checking recursion
     * @param filePath new filepath
     */
    private void validateScript(HashSet<String> files, String filePath) {
        MyConsole console = MyConsole.getInstance();
        MyScanner reader;
        try {
            if (!files.contains(filePath)) {
                files.add(filePath);
                reader = new MyScanner(ScriptParser.readLines(filePath));
                LOGGER.debug("Executing script: " + filePath);
            } else {
                LOGGER.warn("Try of recursion! Script wouldn't execute");
                return;
            }
        } catch (FileNotFoundException | SecurityException e) {
            throw new IncorrectInputException("File don't exist or there are no enough rights");
        }
        console.addText("-----EXECUTE_SCRIPT-----\n(" + filePath + ")");

        ex_script(files, reader);

        files.remove(filePath);
        console.addText("-----ENDED_SCRIPT-----\n(" + filePath + ")");
        LOGGER.debug(String.format("Script: %s executing ended", filePath));
    }

    /**
     * Reads and executes command line by line from reader
     */
    private void ex_script(HashSet<String> files, MyScanner reader) {
        String line = reader.nextLine();
        while (line != null) {
            try {
                if (line.startsWith("execute_script")) {
                    if (line.split(" ").length != 2)
                        throw new IncorrectDataException("Unknown command");
                    validateScript(files, line.split(" ")[1]);
                } else {
                    LOGGER.debug("Executing: " + line);
                    MyConsole.getInstance().addText(settings.getProcessor().exFromScript(reader, line));
                }
            } catch (IncorrectDataException | NullPointerException e) {
                LOGGER.warn(e.getMessage());
            }
            line = reader.nextLine();
        }
    }
}
