package commands;

import collection.DragonCollection;
import dragons.Dragon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Argument command "remove_greater {dragon}". Removes all dragons from collection, that more than inputted dragon
 */
public class RemoveGreaterCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(RemoveGreaterCommand.class);
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    RemoveGreaterCommand(DragonCollection collection) {
        super("remove_greater",
                "remove_greater {dragon} : remove from the collection all items exceeding the specified");
        this.collection = collection;
    }

    /**
     * Removes all dragons from collection, that more than inputted dragon and output info about removed dragons.
     * If there are no such elements in collection, outputs error message
     */
    @Override
    public String execute() {
        return "WTF?? No dragon!!";
    }

    @Override
    public String execute(Dragon maxDragon) {
        Iterator<Dragon> iterator = collection.getItems().iterator();
        int counter = 0;
        StringBuilder output = new StringBuilder();
        while (iterator.hasNext()) {
            Dragon dragon = iterator.next();
            if (dragon.compareTo(maxDragon) > 0) {
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