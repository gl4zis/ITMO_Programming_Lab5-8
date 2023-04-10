package commands;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Non-argument command "exit". Close the application without save changes
 */
public class ExitCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(ExitCommand.class);

    /**
     * Standard constructor, sets collection,  name and description of command
     */
    public ExitCommand() {
        super("exit", "exit : завершить программу (без сохранения в файл)");
    }

    /**
     * Closes this app
     */
    @Override
    public String execute() {
        LOGGER.debug("Correct exit");
        System.exit(0);
        LOGGER.debug("Exit command was executed successfully");
        return "";
    }
}