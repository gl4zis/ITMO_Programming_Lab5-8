package commands;

import database.DataBaseManager;
import exceptions.ExitException;
import exceptions.LoginCollisionException;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Command for sign up new user
 */
public class SignUpCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(SignUpCommand.class);
    private final Connection baseConn;

    SignUpCommand(CommandManager manager) {
        super("sign_up", "");
        this.baseConn = manager.getConn();
    }

    /**
     * Adds in database login and hashed password of new user if it wasn't in database
     */
    @Override
    public String execute(Request request) {
        try {
            User user = request.user();
            DataBaseManager.addUser(baseConn, user);
            return "User was signed up";
        } catch (SQLException e) {
            LOGGER.fatal("No connection with database (");
            throw new ExitException();
        } catch (LoginCollisionException e) {
            return e.getMessage();
        }
    }
}
