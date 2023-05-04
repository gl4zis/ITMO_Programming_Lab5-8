package commands;

import collection.DragonCollection;
import database.DataBaseManager;
import dragons.Dragon;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class InsertCommand extends Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private final DragonCollection collection;
    private final Connection conn;

    InsertCommand(CommandManager manager) {
        super("insert", "insert id {dragon} : add new dragon with inputted id in the collection");
        this.collection = manager.getCollection();
        this.conn = manager.getConn();
    }

    @Override
    public String execute(Request request) {
        int id = (int) request.arg();
        Dragon dragon = request.dragon();
        dragon.setId(id);
        if (!collection.checkId(id)) {
            try {
                DataBaseManager.insertDragon(conn, dragon);
                collection.add(dragon);
                LOGGER.debug("New dragon successfully added in collection");
                return "New dragon successfully added in collection";
            } catch (SQLException e) {
                return "No connection with database (";
            }
        } else return "Dragon with this id already in collection";
    }
}
