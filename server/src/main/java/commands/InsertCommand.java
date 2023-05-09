package commands;

import collection.DragonCollection;
import database.DataBaseManager;
import dragons.Dragon;
import exceptions.ExitException;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Add dragon with inputted id if it's possible
 */
public class InsertCommand extends Command {

    private static final Logger LOGGER = LogManager.getLogger(InsertCommand.class);
    private final DragonCollection collection;
    private final Connection conn;

    /**
     * Constructor sets database connection and dragon collection, that the command works with, description of command
     */
    InsertCommand(CommandManager manager) {
        super("insert id {dragon} : add new dragon with inputted id in the collection");
        this.collection = manager.getCollection();
        this.conn = manager.getConn();
    }

    /**
     * If there are no dragon with requested id, adds it else shows warning
     */
    @Override
    public String execute(Request request) {
        int id = (int) request.arg();
        Dragon dragon = request.dragon();
        dragon.setId(id);
        if (collection.checkId(id))
            return "Dragon with this id already in collection";
        try {
            DataBaseManager.insertDragon(conn, dragon);
            collection.add(dragon);
            LOGGER.debug("New dragon successfully added in collection");
            return "New dragon successfully added in collection";
        } catch (SQLException e) {
            LOGGER.fatal("No connection with database (");
            throw new ExitException();
        }
    }
}
