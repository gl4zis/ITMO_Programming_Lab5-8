package commands;

import collection.DragonCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Non-argument command "info". Outputs info about collection, that application works with
 */
public class InfoCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(InfoCommand.class);
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    InfoCommand(DragonCollection collection) {
        super("info", "info : вывести в стандартный поток вывода информацию о коллекции");
        this.collection = collection;
    }

    /**
     * Outputs info about collection, that application works with
     * (Size, type, date of creation and maximum dragon id)
     */
    @Override
    public String execute() {
        LOGGER.debug("Info command was successfully executed");
        return collection.toString();
    }
}