package commands;

import database.DataBaseManager;
import network.Request;
import user.User;

import java.sql.Connection;
import java.sql.SQLException;

public class ChangePasswordCommand extends Command {
    private final Connection conn;

    ChangePasswordCommand(Connection conn) {
        super("change_password", "change_password password : changes password on your account");
        this.conn = conn;
    }

    @Override
    public String execute(Request request) {
        try {
            User user = request.user();
            String passwd = (String) request.arg();
            DataBaseManager.changePasswd(conn, user, passwd);
            return "Password was changed";
        } catch (SQLException e) {
            return "No connection with database";
        }
    }
}
