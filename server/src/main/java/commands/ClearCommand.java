package commands;

import collection.DragonCollection;
import database.DataBaseManager;
import network.Request;
import user.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Non-argument command "clear". Removes all dragons from the collection
 */
public class ClearCommand extends Command {
    private final DragonCollection collection;

    private final Connection conn;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    ClearCommand(DragonCollection collection, Connection conn) {
        super("clear", "clear : clear the collection");
        this.collection = collection;
        this.conn = conn;
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
            return "No connection with database (";
        }
    }
}
