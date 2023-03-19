package org.application.lab5.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.parsers.InputScriptReader;

import java.util.Iterator;

/**
 * Argument command "remove_lower {dragon}". Removes all dragons from collection, that less than inputted dragon
 */
public class RemoveLowerCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(RemoveLowerCommand.class);
    private final DragonCollection collection;

    /** Constructor, sets collection, that the command works with, name and description of command
     */
    RemoveLowerCommand(DragonCollection collection) {
        super("remove_lower",
                "remove_lower {dragon} : удалить из коллекции все элементы, меньшие, чем заданный");
        this.collection = collection;
    }

    /** Removes all dragons from collection, that less than inputted dragon and output info about removed dragons.
     * If there are no such elements in collection, outputs error message
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public void execute(InputScriptReader reader) {
        Dragon minDragon;
        if (reader == null) {
            minDragon = InputConsoleReader.readDragon();
        } else {
            minDragon = reader.readDragon();
        }
        Iterator<Dragon> iterator = collection.getItems().iterator();
        Dragon dragon;
        int counter = 0;
        while (iterator.hasNext()) {
            dragon = iterator.next();
            if (dragon.compareTo(minDragon) < 0) {
                iterator.remove();
                LOGGER.info("Object with id " + dragon.getId() + ", named " + dragon.getName() + " was removed");
                counter++;
            }
        }
        if (counter == 0) System.out.println("No such elements in the collection");
        LOGGER.debug("RemoveLower command was successfully executed");
    }
}