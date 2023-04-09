package commands;

import collection.DragonCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.InputScriptReader;

/**
 * Non-argument command "clear". Removes all dragons from the collection
 */
public class ClearCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(ClearCommand.class);
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    ClearCommand(DragonCollection collection) {
        super("clear", "clear : очистить коллекцию");
        this.collection = collection;
    }

    /**
     * Removes all dragons from the collection
     *
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public String execute(InputScriptReader reader) {
        collection.clear();
        LOGGER.debug("Clear command was successfully executed");
        return "Collection was cleared";
    }
}
