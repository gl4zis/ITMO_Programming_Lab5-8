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
 * Non-argument command "add_if_min {dragon}"
 */
public class AddIfMinCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(AddIfMinCommand.class);
    private final DragonCollection collection;
    private final DataBaseManager baseMan;

    /**
     * Constructor sets collection and database connection, that the command works with, description of command
     */
    AddIfMinCommand(CommandManager manager) {
        super("add_if_min {dragon} : " +
                "add a new item to the collection if its value is smaller than the smallest item in the collection");
        this.collection = manager.getCollection();
        this.baseMan = manager.getBaseMan();
    }

    /**
     * Adds new dragon from request in database and in collection if there are no dragons less that this
     */
    @Override
    public String execute(Request request) {
        Dragon dragon = request.dragon();
        Dragon minDragon = collection.getMinDragon();
        if (minDragon != null && minDragon.compareTo(dragon) <= 0)
            return "Object is not minimal";
        try {
            dragon.setId(baseMan.addDragon(dragon));
            collection.add(dragon);
        } catch (SQLException e) {
            LOGGER.fatal("No connection with database (");
            throw new ExitException();
        }
        LOGGER.info("New dragon successfully added in the collection");
        return "New dragon successfully added in the collection";
    }
}
