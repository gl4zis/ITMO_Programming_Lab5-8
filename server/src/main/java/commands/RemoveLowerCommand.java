package commands;

import collection.DragonCollection;
import database.DataBaseManager;
import dragons.Dragon;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Argument command "remove_lower {dragon}". Removes all dragons from collection, that less than inputted dragon
 */
public class RemoveLowerCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(RemoveLowerCommand.class);
    private final DragonCollection collection;
    private final Connection conn;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    RemoveLowerCommand(CommandManager manager) {
        super("remove_lower",
                "remove_lower {dragon} : remove all items from the collection that are smaller than the specified");
        this.collection = manager.getCollection();
        this.conn = manager.getConn();
    }

    /**
     * Removes all dragons from collection, that less than inputted dragon and output info about removed dragons.
     * If there are no such elements in collection, outputs error message
     */
    @Override
    public String execute(Request request) {
        Dragon minDragon = request.dragon();
        User user = request.user();
        StringBuilder output = new StringBuilder();
        collection.getItems().stream()
                .filter(p -> p.compareTo(minDragon) < 0).sorted(Dragon.coordComp).forEach(p -> {
                    try {
                        DataBaseManager.removeDragon(conn, p, user);
                    } catch (SQLException e) {
                        return;
                    }
                    collection.remove(p);
                    String outLine = "Removed dragon " + p.getName() + " with id: " + p.hashCode();
                    LOGGER.info(outLine);
                    output.append(outLine).append('\n');
                });
        if (output.length() > 0) {
            output.deleteCharAt(output.length() - 1);
            return output.toString();
        } else return "No such elements in collection";
    }
}