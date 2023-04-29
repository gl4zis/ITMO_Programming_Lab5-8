package commands;

import collection.DragonCollection;
import database.DataBaseManager;
import dragons.Dragon;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Non-argument command "add_if_min {dragon}"
 */
public class AddIfMinCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(AddIfMinCommand.class);
    private final DragonCollection collection;
    private final Connection conn;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    AddIfMinCommand(DragonCollection collection, Connection conn) {
        super("add_if_min",
                "add_if_min {dragon} : " +
                        "add a new item to the collection if its value is smaller than the smallest item in the collection");
        this.collection = collection;
        this.conn = conn;
    }

    /**
     * Reads all dragon vars, creates new dragon object and adds it in the collection if there are no dragons less that this
     */
    @Override
    public String execute(Request request) {
        Dragon dragon = request.dragon();
        Dragon minDragon = collection.getMinDragon();
        if (minDragon == null || collection.getMinDragon().compareTo(dragon) > 0) {
            try {
                dragon.setId(DataBaseManager.addDragon(conn, dragon));
                collection.add(dragon);
            } catch (SQLException e) {
                return "No connection with database (";
            }
            LOGGER.info("New dragon successfully added in the collection");
            return "New dragon successfully added in the collection";
        } else {
            return "Object is not minimal";
        }
    }
}
