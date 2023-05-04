package commands;

import collection.DragonCollection;
import database.DataBaseManager;
import dragons.Dragon;
import exceptions.PermissionDeniedException;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Argument command "update id {dragon}". Change dragon vars on another. Finds dragon in collection by its id
 */
public class UpdateCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(UpdateCommand.class);
    private final DragonCollection collection;
    private final Connection conn;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    UpdateCommand(CommandManager manager) {
        super("update", "update id {element} : " +
                "update the value of the collection item whose id is equal to the given one");
        this.collection = manager.getCollection();
        this.conn = manager.getConn();
    }

    /**
     * Change dragon vars on another. Finds dragon in collection by its id.
     * Outputs error message, if wrong argument or if there are no such element in collection
     */
    @Override
    public String execute(Request request) {
        try {
            int id = (int) request.arg();
            User user = request.user();
            Dragon dragon = collection.find(id);
            DataBaseManager.updateDragon(conn, id, request.dragon(), user);
            dragon.update(request.dragon());
            LOGGER.info("Dragon was updated");
            return "Dragon was updated";
        } catch (SQLException e) {
            return "No connection with database (";
        } catch (PermissionDeniedException e) {
            return "It is not your dragon! " + e.getMessage();
        }
    }
}