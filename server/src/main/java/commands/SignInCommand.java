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
    private final Connection baseConn;

    SignInCommand(CommandManager manager) {
        super("sign_in", "");
        this.baseConn = manager.getConn();
    }

    /**
     * Checks if this user is in database
     */
    @Override
    public String execute(Request request) {
        User user = request.user();
        try {
            DataBaseManager.signInUser(baseConn, user);
            return "User was signed in";
        } catch (SQLException e) {
            LOGGER.fatal("No connection with database (");
            throw new ExitException();
        } catch (WrongPasswordException | NoSuchUserException e) {
            return e.getMessage();
        }
    }
}
