package commands;

import collection.DragonCollection;
import database.DataBaseManager;
import dragons.Dragon;
import exceptions.ExitException;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Non-argument command "add {dragon}". Adds new dragon object in collection.
 * Needs to type all non-auto-generated dragon vars after command
 */
public class AddCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(AddCommand.class);
    private final DragonCollection collection;
    private final DataBaseManager baseMan;

    /**
     * Constructor sets collection and database connection, that the command works with, description of command
     */
    AddCommand(CommandManager manager) {
        super("add {dragon} : add a new item to the collection");
        this.collection = manager.getCollection();
        this.baseMan = manager.getBaseMan();
    }

    /**
     * Adds new dragon from request in database and in collection
     */
    @Override
    public String execute(Request request) {
        Dragon dragon = request.dragon();
        try {
            dragon.setId(baseMan.addDragon(dragon));
            collection.add(dragon);
        } catch (SQLException e) {
            LOGGER.fatal("No connection with database (");
            throw new ExitException();
        }
        LOGGER.info("Dragon successfully added in the collection");
        return "Dragon successfully added in the collection";
    }
}
