package commands;

import collection.DragonCollection;
import dragons.Dragon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.InputConsoleReader;
import parsers.InputScriptReader;

/**
 * Non-argument command "add {dragon}". Adds new dragon object in collection.
 * Needs to type all non-auto-generated dragon vars after command
 */
public class AddCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(AddCommand.class);
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    AddCommand(DragonCollection collection) {
        super("add", "add {dragon} : добавить новый элемент в коллекцию");
        this.collection = collection;
    }

    /**
     * Reads all dragon vars, creates new dragon object and adds it in the collection
     *
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public String execute(InputScriptReader reader) {
        Dragon dragon;
        if (reader == null) {
            dragon = InputConsoleReader.readDragon();
        } else {
            dragon = reader.readDragon();
        }
        collection.add(dragon);
        LOGGER.info("Dragon successfully added in the collection");
        LOGGER.debug("Add command was successfully executed");
        return "Dragon successfully added in the collection";
    }

    @Override
    public String execute(InputScriptReader reader, Dragon dragon) {
        collection.add(dragon);
        LOGGER.info("Dragon successfully added in the collection");
        LOGGER.debug("Add command was successfully executed");
        return "Dragon successfully added in the collection";
    }
}
