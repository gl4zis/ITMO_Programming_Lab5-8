package commands;

import collection.DragonCollection;
import exceptions.NonUniqueValueException;
import exceptions.ObjectNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Argument command "remove_by_id id". Removes dragon from the collection by its id
 */
public class RemoveByIdCommand extends ArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(RemoveByIdCommand.class);
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    RemoveByIdCommand(DragonCollection collection) {
        super("remove_by_id", "remove_by_id id : remove an item from the collection by its id");
        this.collection = collection;
    }

    /**
     * Removes dragon from the collection by its id.
     * If there is incorrect argument or there is no dragon with such id in collection, outputs error messages
     *
     * @param arg id of dragon, which you want to remove
     */
    @Override
    public String execute(String arg) {
        try {
            int id = Integer.parseInt(arg);
            collection.remove(collection.find(id));
            return "Dragon was successfully removed";
        } catch (NumberFormatException e) {
            LOGGER.warn("Incorrect command argument");
            return "Incorrect command argument";
        } catch (ObjectNotFoundException e) {
            LOGGER.debug(e.getMessage());
            return "There is no such element in the collection";
        } catch (NonUniqueValueException e) {
            LOGGER.info(e.getMessage());
            return e.getMessage() + "Try to input more numbers";
        }
    }
}
