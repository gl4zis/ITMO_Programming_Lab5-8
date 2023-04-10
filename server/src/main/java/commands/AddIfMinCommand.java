package commands;

import collection.DragonCollection;
import dragons.Dragon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Non-argument command "add_if_min {dragon}"
 */
public class AddIfMinCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(NonArgsCommand.class);
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    AddIfMinCommand(DragonCollection collection) {
        super("add_if_min",
                "add_if_min {dragon} : " +
                        "add a new item to the collection if its value is smaller than the smallest item in the collection");
        this.collection = collection;
    }

    /**
     * Reads all dragon vars, creates new dragon object and adds it in the collection if there are no dragons less that this
     */
    @Override
    public String execute() {
        return "WTF?? No dragon!!";
    }

    @Override
    public String execute(Dragon dragon) {
        Dragon minDragon = collection.getMinDragon();
        if (minDragon == null || collection.getMinDragon().compareTo(dragon) > 0) {
            collection.add(dragon);
            LOGGER.info("New dragon successfully added in the collection");
            LOGGER.debug("AddIfMin command was successfully executed");
            return "New dragon successfully added in the collection";
        } else {
            LOGGER.debug("AddIfMin command was successfully executed");
            return "Object is not minimal";
        }
    }
}
