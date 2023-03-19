package org.application.lab5.commands;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.application.lab5.parsers.InputScriptReader;

/**
 * Non-argument command "exit". Close the application without save changes
 */
public class ExitCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(ExitCommand.class);

    /** Standard constructor, sets name and description of command
     */
    ExitCommand() {
        super("exit", "exit : завершить программу (без сохранения в файл)");
    }

    /** Closes this app
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public void execute(InputScriptReader reader) {
        LOGGER.debug("Correct exit");
        System.exit(0);
    }
}