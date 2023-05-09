package commands;

import collection.DragonCollection;
import database.DataBaseManager;
import exceptions.ExitException;
import exceptions.PermissionDeniedException;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Argument command "remove_by_id id". Removes dragon from the collection by its id
 */
public class RemoveByIdCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(RemoveByIdCommand.class);
    private final DragonCollection collection;
    private final Connection conn;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    RemoveByIdCommand(CommandManager manager) {
        super("remove_by_id", "remove_by_id id : remove an item from the collection by its id");
        this.collection = manager.getCollection();
        this.conn = manager.getConn();
    }

    /**
     * Removes dragon from the collection by its id.
     * If there is incorrect argument or there is no dragon with such id in collection, outputs error messages
     */
    @Override
    public String execute(Request request) {
        int id = (int) request.arg();
        User user = request.user();
        try {
            DataBaseManager.removeById(conn, id, user);
            collection.remove(collection.find(id));
            LOGGER.info("Dragon was successfully removed");
            return "Dragon was successfully removed";
        } catch (SQLException e) {
            LOGGER.fatal("No connection with database (");
            throw new ExitException();
        } catch (PermissionDeniedException e) {
            return "It is not your dragon! " + e.getMessage();
        }
    }
}
