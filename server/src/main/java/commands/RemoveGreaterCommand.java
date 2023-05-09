package commands;

import collection.DragonCollection;
import database.DataBaseManager;
import dragons.Dragon;
import exceptions.ExitException;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Argument command "remove_greater {dragon}".
 * Removes all dragons from collection, that more than inputted dragon
 */
public class RemoveGreaterCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(RemoveGreaterCommand.class);
    private final DragonCollection collection;
    private final Connection conn;

    /**
     * Constructor sets collection and database connection, that the command works with, description of command
     */
    RemoveGreaterCommand(CommandManager manager) {
        super("remove_greater {dragon} : remove from the collection all items exceeding the specified");
        this.collection = manager.getCollection();
        this.conn = manager.getConn();
    }

    /**
     * Removes all dragons from collection, that more than inputted dragon and was created by current user,
     * outputs info about removed dragons.
     * If there are no such elements in collection, outputs error message
     */
    @Override
    public String execute(Request request) {
        Dragon maxDragon = request.dragon();
        User user = request.user();
        StringBuilder output = new StringBuilder();
        collection.getItems().stream()
                .filter(p -> p.compareTo(maxDragon) > 0).sorted(Dragon.coordComp).forEach(p -> removeDragon(p, user, output));
        if (output.length() > 0) {
            output.deleteCharAt(output.length() - 1);
            return output.toString();
        } else return "No such elements in collection";
    }

    /**
     * Removes one dragon and collects info about it
     *
     * @param p      removing dragon
     * @param user   current user
     * @param output StringBuilder for collect info
     */
    private void removeDragon(Dragon p, User user, StringBuilder output) {
        try {
            DataBaseManager.removeDragon(conn, p, user);
            collection.remove(p);
            String outLine = "Removed dragon " + p.getName() + " with id: " + p.hashCode();
            LOGGER.info(outLine);
            output.append(outLine).append('\n');
        } catch (SQLException e) {
            LOGGER.fatal("No connection with database (");
            throw new ExitException();
        }
    }
}