package commands;

import collection.DragonCollection;
import dragons.Dragon;
import exceptions.IdCollisionException;
import general.UniqueIdGenerator;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Non-argument command "add {dragon}". Adds new dragon object in collection.
 * Needs to type all non-auto-generated dragon vars after command
 */
public class AddCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(AddCommand.class);
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    AddCommand(DragonCollection collection) {
        super("add", "add {dragon} : add a new item to the collection");
        this.collection = collection;
    }

    /**
     * Reads all dragon vars, creates new dragon object and adds it in the collection
     */
    @Override
    public String execute(Request request) {
        Dragon dragon = request.getDragon();
        while (true) {
            try {
                collection.add(dragon);
                break;
            } catch (IdCollisionException ignored) {
                dragon.setId(UniqueIdGenerator.getIntId());
            }
        }
        LOGGER.info("Dragon successfully added in the collection");
        return "Dragon successfully added in the collection";
    }
}
