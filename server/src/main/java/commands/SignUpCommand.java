package commands;

import database.DataBaseManager;
import exceptions.LoginCollisionException;
import network.Request;
import user.User;

import java.sql.Connection;
import java.sql.SQLException;

public class SignUpCommand extends Command {

    private final Connection baseConn;

    SignUpCommand(CommandManager manager) {
        super("sign_up", "");
        this.baseConn = manager.getConn();
    }

    @Override
    public String execute(Request request) {
        try {
            User user = request.user();
            DataBaseManager.addUser(baseConn, user);
            return "User was signed up";
        } catch (SQLException e) {
            return "No connection with data base (";
        } catch (LoginCollisionException e) {
            return e.getMessage();
        }
    }
}
