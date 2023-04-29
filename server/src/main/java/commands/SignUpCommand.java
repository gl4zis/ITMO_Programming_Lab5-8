package commands;

import database.DataBaseParser;
import exceptions.LoginCollisionException;
import network.Request;
import user.User;

import java.sql.Connection;
import java.sql.SQLException;

public class SignUpCommand extends Command {

    private final Connection baseConn;

    SignUpCommand(Connection baseConn) {
        super("sign_up", "");
        this.baseConn = baseConn;
    }

    @Override
    public String execute(Request request) {
        try {
            User user = request.user();
            DataBaseParser.addUser(baseConn, user);
            return "User was signed up";
        } catch (SQLException e) {
            return "No connection with data base (";
        } catch (LoginCollisionException e) {
            return e.getMessage();
        }
    }
}
