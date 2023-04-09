package commands;

import collection.DragonCollection;
import dragons.Dragon;
import exceptions.NonUniqueValueException;
import exceptions.ObjectNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.InputConsoleReader;
import parsers.InputScriptReader;

/**
 * Argument command "update id {dragon}". Change dragon vars on another. Finds dragon in collection by its id
 */
public class UpdateCommand extends ArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(UpdateCommand.class);
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    UpdateCommand(DragonCollection collection) {
        super("update", "update id {element} : обновить значение элемента коллекции, id которого равен заданному");
        this.collection = collection;
    }

    /**
     * Change dragon vars on another. Finds dragon in collection by its id.
     * Outputs error message, if wrong argument or if there are no such element in collection
     *
     * @param reader reader of file from that gives data, if null data gives from console
     * @param arg    id of dragon, which you want to update
     */
    @Override
    public String execute(InputScriptReader reader, String arg) {
        try {
            int id = Integer.parseInt(arg);
            collection.find(id);
            Dragon dragon;
            if (reader == null) {
                dragon = InputConsoleReader.readDragon();
            } else {
                dragon = reader.readDragon();
            }
            collection.find(id).update(dragon);
            collection.saved = false;
            LOGGER.info("Dragon was updated");
            LOGGER.debug("Update command successfully executed");
            return "Dragon was updated";
        } catch (NumberFormatException e) {
            LOGGER.warn("Incorrect command argument");
            LOGGER.debug("Update command successfully executed");
            return "Incorrect command argument";
        } catch (ObjectNotFoundException e) {
            LOGGER.debug(e.getMessage());
            LOGGER.debug("Update command successfully executed");
            return "No such element in the collection";
        } catch (NonUniqueValueException e) {
            LOGGER.info(e.getMessage());
            LOGGER.debug("Update command successfully executed");
            return e.getMessage() + "\nTry to input more numbers";
        }
    }

    @Override
    public String execute(InputScriptReader reader, String arg, Dragon dragon) {
        try {
            int id = Integer.parseInt(arg);
            collection.find(id).update(dragon);
            collection.saved = false;
            LOGGER.info("Dragon was updated");
            LOGGER.debug("Update command successfully executed");
            return "Dragon was updated";
        } catch (NumberFormatException e) {
            LOGGER.warn("Incorrect command argument");
            LOGGER.debug("Update command successfully executed");
            return "Incorrect command argument";
        } catch (ObjectNotFoundException e) {
            LOGGER.debug(e.getMessage());
            LOGGER.debug("Update command successfully executed");
            return "No such element in the collection";
        } catch (NonUniqueValueException e) {
            LOGGER.info(e.getMessage());
            LOGGER.debug("Update command successfully executed");
            return e.getMessage() + "\nTry to input more numbers";
        }
    }
}