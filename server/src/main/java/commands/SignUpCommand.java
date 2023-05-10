package commands;

import database.DataBaseManager;
import exceptions.ExitException;
import exceptions.LoginCollisionException;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user.User;

import java.sql.SQLException;

/**
 * Command for sign up new user
 */
public class SignUpCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(SignUpCommand.class);
    private final DataBaseManager baseMan;

    /**
     * Constructor sets database connection, that the command works with
     */
    SignUpCommand(CommandManager manager) {
        super("");
        this.baseMan = manager.getBaseMan();
    }

    /**
     * Adds in database login and hashed password of new user.
     * Outputs 'User was signed up' if all ok.
     * Outputs error message if user with this login already exists
     */
    @Override
    public String execute(Request request) {
        try {
            User user = request.user();
            baseMan.addUser(user);
            return "User was signed up";
        } catch (SQLException e) {
            LOGGER.fatal("No connection with database (");
            throw new ExitException();
        } catch (LoginCollisionException e) {
            return e.getMessage();
        }
    }
}
