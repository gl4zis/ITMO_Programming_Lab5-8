package commands;

import collection.DragonCollection;
import database.DataBaseManager;
import dragons.Dragon;
import exceptions.ExitException;
import exceptions.PermissionDeniedException;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Argument command "update id {dragon}".
 * Change dragon parameters on another.
 */
public class UpdateCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(UpdateCommand.class);
    private final DragonCollection collection;
    private final DataBaseManager baseMan;

    /**
     * Constructor sets collection and database connection, that the command works with, description of command
     */
    UpdateCommand(CommandManager manager) {
        super("update id {element} : " +
                "update the value of the collection item whose id is equal to the given one");
        this.collection = manager.getCollection();
        this.baseMan = manager.getBaseMan();
    }

    /**
     * Change dragon parameters on another.
     * Finds dragon in collection by its id.
     * Outputs error message, if there are no such element in collection
     * or if creator of this dragon not equals to current user
     */
    @Override
    public String execute(Request request) {
        try {
            int id = (int) request.arg();
            User user = request.user();
            Dragon dragon = collection.find(id);
            baseMan.updateDragon(id, request.dragon(), user);
            dragon.update(request.dragon());
            LOGGER.info("Dragon was updated");
            return "Dragon was updated";
        } catch (SQLException e) {
            LOGGER.fatal("No connection with database (");
            throw new ExitException();
        } catch (PermissionDeniedException e) {
            return "It is not your dragon! " + e.getMessage();
        }
    }
}