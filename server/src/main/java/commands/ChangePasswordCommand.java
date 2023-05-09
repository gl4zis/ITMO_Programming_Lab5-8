package commands;

import database.DataBaseManager;
import exceptions.ExitException;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Command for changing user password
 */
public class ChangePasswordCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(ChangePasswordCommand.class);
    private final Connection conn;

    /**
     * Constructor sets database connection
     */
    ChangePasswordCommand(CommandManager manager) {
        super("change_password", "change_password password : changes password on your account");
        this.conn = manager.getConn();
    }

    /**
     * Change current user's password in database to new
     */
    @Override
    public String execute(Request request) {
        try {
            User user = request.user();
            String passwd = (String) request.arg();
            DataBaseManager.changePasswd(conn, user, passwd);
            return "Password was changed";
        } catch (SQLException e) {
            LOGGER.fatal("No connection with database (");
            throw new ExitException();
        }
    }
}
