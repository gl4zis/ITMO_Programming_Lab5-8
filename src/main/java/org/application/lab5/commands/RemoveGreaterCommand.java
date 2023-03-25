package org.application.lab5.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.parsers.InputScriptReader;

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
    public void execute(InputScriptReader reader) {
        Dragon maxDragon;
        if (reader == null) {
            maxDragon = InputConsoleReader.readDragon();
        } else {
            maxDragon = reader.readDragon();
        }
        Iterator<Dragon> iterator = collection.getItems().iterator();
        Dragon dragon;
        int counter = 0;
        while (iterator.hasNext()) {
            dragon = iterator.next();
            if (dragon.compareTo(maxDragon) > 0) {
                iterator.remove();
                LOGGER.info("Object with id " + dragon.getId() + ", named " + dragon.getName() + " was removed");
                counter++;
            }
        }
        if (counter == 0) System.out.println("No such elements in the collection");
        LOGGER.debug("RemoveGreater command was successfully executed");
    }
}