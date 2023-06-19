package commands;

import database.DataBaseManager;
import dragons.DragonCollection;
import exceptions.ExitException;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user.User;

import java.sql.SQLException;

/**
 * Non-argument command "clear". Removes all dragons from the collection
 */
public class ClearCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(ClearCommand.class);
    private final DragonCollection collection;
    private final DataBaseManager baseMan;

    /**
     * Constructor sets collection and database connection, that the command works with, description of command
     */
    ClearCommand(CommandManager manager) {
        super("clear : clear the collection (delete all your dragons)");
        this.collection = manager.getCollection();
        this.baseMan = manager.getBaseMan();
    }

    /**
     * Removes all dragons, created by current user, from the collection
     */
    @Override
    public String execute(Request request) {
        User user = request.user();
        try {
            baseMan.clearDragons(user);
            collection.clear(user);
            return "Your dragons in collection was cleared";
        } catch (SQLException e) {
            LOGGER.fatal("No connection with database (");
            throw new ExitException();
        }
    }
}
