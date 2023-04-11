package commands;

import collection.DragonCollection;
import dragons.Dragon;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Argument command "remove_by_id id". Removes dragon from the collection by its id
 */
public class RemoveByIdCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(RemoveByIdCommand.class);
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    RemoveByIdCommand(DragonCollection collection) {
        super("remove_by_id", "remove_by_id id : remove an item from the collection by its id");
        this.collection = collection;
    }

    /**
     * Removes dragon from the collection by its id.
     * If there is incorrect argument or there is no dragon with such id in collection, outputs error messages
     */
    @Override
    public String execute(Request request) {
        try {
            int id = (int) request.getArg();
            Dragon dragon = collection.find(id);
            if (dragon != null) {
                collection.remove(collection.find(id));
                LOGGER.info("Dragon was successfully removed");
                return "Dragon was successfully removed";
            } else return "No such elements in collection";
        } catch (NumberFormatException e) {
            LOGGER.warn("Incorrect command argument");
            return "Incorrect command argument";
        }
    }
}
