package commands;

import collection.DragonCollection;
import database.DataBaseManager;
import exceptions.ExitException;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Non-argument command "clear". Removes all dragons from the collection
 */
public class ClearCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(ClearCommand.class);
    private final DragonCollection collection;
    private final Connection conn;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    ClearCommand(CommandManager manager) {
        super("clear", "clear : clear the collection");
        this.collection = manager.getCollection();
        this.conn = manager.getConn();
    }

    /**
     * Removes all dragons from the collection
     */
    @Override
    public String execute(Request request) {
        User user = request.user();
        try {
            DataBaseManager.clearDragons(conn, user);
            collection.clear(user);
            return "Your dragons in collection was cleared";
        } catch (SQLException e) {
            LOGGER.fatal("No connection with database (");
            throw new ExitException();
        }
    }
}
