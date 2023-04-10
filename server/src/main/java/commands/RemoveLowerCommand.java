package commands;

import collection.DragonCollection;
import dragons.Dragon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Argument command "remove_lower {dragon}". Removes all dragons from collection, that less than inputted dragon
 */
public class RemoveLowerCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(RemoveLowerCommand.class);
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    RemoveLowerCommand(DragonCollection collection) {
        super("remove_lower",
                "remove_lower {dragon} : remove all items from the collection that are smaller than the specified");
        this.collection = collection;
    }

    /**
     * Removes all dragons from collection, that less than inputted dragon and output info about removed dragons.
     * If there are no such elements in collection, outputs error message
     */
    @Override
    public String execute() {
        return "WTF?? No dragon!!";
    }

    @Override
    public String execute(Dragon minDragon) {
        Iterator<Dragon> iterator = collection.getItems().iterator();
        int counter = 0;
        StringBuilder output = new StringBuilder();
        while (iterator.hasNext()) {
            Dragon dragon = iterator.next();
            if (dragon.compareTo(minDragon) < 0) {
                iterator.remove();
                String message = "Object with id " + dragon.getId() + ", named " + dragon.getName() + " was removed";
                LOGGER.info(message);
                output.append(message).append('\n');
                counter++;
            }
        }
        if (counter == 0) {
            return "No such elements in the collection";
        } else {
            output.deleteCharAt(output.length() - 1);
            return output.toString();
        }
    }
}