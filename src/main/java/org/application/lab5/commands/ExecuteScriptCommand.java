package org.application.lab5.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.application.lab5.exceptions.IncorrectDataException;
import org.application.lab5.parsers.InputScriptReader;
import org.application.lab5.parsers.StringModificator;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

/**
 * Argument command "execute_script file_path". Executes all commands from the script file
 */
public class ExecuteScriptCommand extends ArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(ExecuteScriptCommand.class);
    private final Set<String> files = new HashSet<>();
    private final CommandManager commandManager;

    /** Constructor, sets command manager, that the command works with, name and description of command
     */
    ExecuteScriptCommand(CommandManager commandManager) {
        super("execute_script", "execute_script file_name : считать и исполнить скрипт из указанного файла");
        this.commandManager = commandManager;
    }

    /** Read lines from file, finds commands in them and try to execute them.
     * If something wrong in command just skips it.
     * If catches recursion or there are no such file don't execute script and outputs error message
     * @param reader   reader of file from that gives data, if null data gives from console
     * @param filePath full path to the file which have script to execute
     */
    @Override
    public void execute(InputScriptReader reader, String filePath) {
        filePath = StringModificator.filePathFormat(filePath);
        if (!files.contains(filePath)) {
            try {
                InputScriptReader newReader = new InputScriptReader(filePath);
                files.add(filePath);
                String line = newReader.findNextCommand(commandManager);
                while (line != null) {
                    System.out.println();
                    LOGGER.info("Executing: " + line);
                    try {
                        commandManager.seekCommand(newReader, line);
                    } catch (NullPointerException | IncorrectDataException e) {
                        LOGGER.warn("ERROR! Command didn't execute");
                    }
                    line = newReader.findNextCommand(commandManager);
                }
                LOGGER.info("End of executing script");
            } catch (FileNotFoundException | SecurityException e) {
                LOGGER.error("File didn't find or there is no access to file");
            }
            files.remove(filePath);
        } else LOGGER.warn("Try of recursion! Script wouldn't execute");
        LOGGER.debug("ExecuteScript command was successfully executed");
    }
}