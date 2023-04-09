package commands;

import collection.DragonCollection;
import dragons.Dragon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.InputConsoleReader;
import parsers.InputScriptReader;

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
                "remove_lower {dragon} : удалить из коллекции все элементы, меньшие, чем заданный");
        this.collection = collection;
    }

    /**
     * Removes all dragons from collection, that less than inputted dragon and output info about removed dragons.
     * If there are no such elements in collection, outputs error message
     *
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public String execute(InputScriptReader reader) {
        Dragon minDragon;
        if (reader == null) {
            minDragon = InputConsoleReader.readDragon();
        } else {
            minDragon = reader.readDragon();
        }
        Iterator<Dragon> iterator = collection.getItems().iterator();
        int counter = 0;
        ArrayList<Dragon> dragons = new ArrayList<>();
        while (iterator.hasNext()) {
            Dragon dragon = iterator.next();
            if (dragon.compareTo(minDragon) < 0) {
                iterator.remove();
                dragons.add(dragon);
                LOGGER.info("Object with id " + dragon.getId() + ", named " + dragon.getName() + " was removed");
                counter++;
            }
        }
        if (counter == 0) {
            LOGGER.debug("RemoveLower command was successfully executed");
            return "No such elements in the collection";
        } else {
            StringBuilder line = new StringBuilder();
            for (Dragon dragon : dragons) {
                line.append("Object with id " + dragon.getId() + ", named " + dragon.getName() + " was removed");
            }
            LOGGER.debug("RemoveLower command was successfully executed");
            return line.toString();
        }
    }

    @Override
    public String execute(InputScriptReader reader, Dragon minDragon) {
        Iterator<Dragon> iterator = collection.getItems().iterator();
        int counter = 0;
        ArrayList<Dragon> dragons = new ArrayList<>();
        while (iterator.hasNext()) {
            Dragon dragon = iterator.next();
            if (dragon.compareTo(minDragon) < 0) {
                iterator.remove();
                dragons.add(dragon);
                LOGGER.info("Object with id " + dragon.getId() + ", named " + dragon.getName() + " was removed");
                counter++;
            }
        }
        if (counter == 0) {
            LOGGER.debug("RemoveLower command was successfully executed");
            return "No such elements in the collection";
        } else {
            StringBuilder line = new StringBuilder();
            for (Dragon dragon : dragons) {
                line.append("Object with id " + dragon.getId() + ", named " + dragon.getName() + " was removed");
            }
            LOGGER.debug("RemoveLower command was successfully executed");
            return line.toString();
        }
    }
}