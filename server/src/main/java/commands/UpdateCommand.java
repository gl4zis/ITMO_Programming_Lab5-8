package commands;

import collection.DragonCollection;
import dragons.Dragon;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Argument command "update id {dragon}". Change dragon vars on another. Finds dragon in collection by its id
 */
public class UpdateCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(UpdateCommand.class);
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    UpdateCommand(DragonCollection collection) {
        super("update", "update id {element} : " +
                "refresh the value of the collection item whose id is equal to the given one");
        this.collection = collection;
    }

    /**
     * Change dragon vars on another. Finds dragon in collection by its id.
     * Outputs error message, if wrong argument or if there are no such element in collection
     */
    @Override
    public String execute(Request request) {
        try {
            int id = (int) request.getArg();
            Dragon dragon = collection.find(id);
            if (dragon != null) {
                dragon.update(request.getDragon());
                LOGGER.info("Dragon was updated");
                return "Dragon was updated";
            } else return "No such element in collection";
        } catch (NumberFormatException e) {
            LOGGER.warn("Incorrect command argument");
            return "Incorrect command argument";
        }
    }
}