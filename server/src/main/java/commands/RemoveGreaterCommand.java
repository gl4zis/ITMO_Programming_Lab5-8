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
                "remove_greater {dragon} : удалить из коллекции все элементы, превышающие заданный");
        this.collection = collection;
    }

    /**
     * Removes all dragons from collection, that more than inputted dragon and output info about removed dragons.
     * If there are no such elements in collection, outputs error message
     *
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public String execute() {
        return "WTF?? No dragon!!";
    }

    @Override
    public String execute(Dragon maxDragon) {
        Iterator<Dragon> iterator = collection.getItems().iterator();
        int counter = 0;
        ArrayList<Dragon> dragons = new ArrayList<>();
        while (iterator.hasNext()) {
            Dragon dragon = iterator.next();
            if (dragon.compareTo(maxDragon) > 0) {
                iterator.remove();
                dragons.add(dragon);
                LOGGER.info("Object with id " + dragon.getId() + ", named " + dragon.getName() + " was removed");
                counter++;
            }
        }
        if (counter == 0) {
            LOGGER.debug("RemoveGreater command was successfully executed");
            return "No such elements in the collection";
        } else {
            StringBuilder line = new StringBuilder();
            for (Dragon dragon : dragons) {
                line.append("Object with id ").append(dragon.getId()).append(", named ").append(dragon.getName()).append(" was removed");
            }
            LOGGER.debug("RemoveGreater command was successfully executed");
            return line.toString();
        }
    }
}