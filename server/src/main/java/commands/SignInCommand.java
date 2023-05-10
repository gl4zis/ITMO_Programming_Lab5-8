package commands;

import database.DataBaseManager;
import exceptions.ExitException;
import exceptions.NoSuchUserException;
import exceptions.WrongPasswordException;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Command for sign in user
 */
public class SignInCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(SignInCommand.class);
    private final DataBaseManager baseMan;

    /**
     * Constructor sets database connection, that the command works with
     */
    SignInCommand(CommandManager manager) {
        super("");
        this.baseMan = manager.getBaseMan();
    }

    /**
     * Checks if this user is in database.
     * If all ok, outputs 'User was signed in'.
     * Outputs error message if there is incorrect login or password
     */
    @Override
    public String execute(Request request) {
        User user = request.user();
        try {
            baseMan.signInUser(user);
            return "User was signed in";
        } catch (SQLException e) {
            LOGGER.fatal("No connection with database (");
            throw new ExitException();
        } catch (WrongPasswordException | NoSuchUserException e) {
            return e.getMessage();
        }
    }
}
