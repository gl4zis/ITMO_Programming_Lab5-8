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
 * Add dragon with inputted id if it's possible
 */
public class InsertCommand extends Command {

    private static final Logger LOGGER = LogManager.getLogger(InsertCommand.class);
    private final DragonCollection collection;
    private final DataBaseManager baseMan;

    /**
     * Constructor sets database connection and dragon collection, that the command works with, description of command
     */
    InsertCommand(CommandManager manager) {
        super("insert key {dragon} : add new dragon with inputted (String) key in the collection");
        this.collection = manager.getCollection();
        this.baseMan = manager.getBaseMan();
    }

    /**
     * If there are no dragon with requested id, adds it else shows warning
     */
    @Override
    public String execute(Request request) {
        String key = (String) request.arg();
        Dragon dragon = request.dragon();
        dragon.setKey(key);
        try {
            baseMan.insertDragon(dragon);
            collection.add(dragon);
            LOGGER.debug("New dragon successfully added in collection");
            return "New dragon successfully added in collection";
        } catch (SQLException e) {
            LOGGER.fatal("No connection with database (");
            throw new ExitException();
        }
    }
}
