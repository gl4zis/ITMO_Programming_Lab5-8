package commands;


import collection.DragonCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.InputConsoleReader;
import parsers.InputScriptReader;

/**
 * Non-argument command "exit". Close the application without save changes
 */
public class ExitCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(ExitCommand.class);
    private final DragonCollection collection;

    /**
     * Standard constructor, sets collection,  name and description of command
     */
    public ExitCommand(DragonCollection collection) {
        super("exit", "exit : завершить программу (без сохранения в файл)");
        this.collection = collection;
    }

    /**
     * Closes this app
     *
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public String execute(InputScriptReader reader) {
        LOGGER.debug("Correct exit");
        System.exit(0);
        LOGGER.debug("Exit command was executed successfully (without ending app)");
        return "";
    }
}