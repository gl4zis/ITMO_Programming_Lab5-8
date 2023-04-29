package commands;

import database.DataBaseParser;
import exceptions.NoSuchUserException;
import exceptions.WrongPasswordException;
import network.Request;
import user.User;

import java.sql.Connection;
import java.sql.SQLException;

public class SignInCommand extends Command {

    private final Connection baseConn;

    SignInCommand(Connection baseConn) {
        super("sign_in", "");
        this.baseConn = baseConn;
    }

    @Override
    public String execute(Request request) {
        User user = request.user();
        try {
            DataBaseParser.signInUser(baseConn, user);
            return "User was signed in";
        } catch (SQLException e) {
            return "No connection with data base (";
        } catch (WrongPasswordException | NoSuchUserException e) {
            return e.getMessage();
        }
    }
}
