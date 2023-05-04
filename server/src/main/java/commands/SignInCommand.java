package commands;

import database.DataBaseManager;
import exceptions.NoSuchUserException;
import exceptions.WrongPasswordException;
import network.Request;
import user.User;

import java.sql.Connection;
import java.sql.SQLException;

public class SignInCommand extends Command {

    private final Connection baseConn;

    SignInCommand(CommandManager manager) {
        super("sign_in", "");
        this.baseConn = manager.getConn();
    }

    @Override
    public String execute(Request request) {
        User user = request.user();
        try {
            DataBaseManager.signInUser(baseConn, user);
            return "User was signed in";
        } catch (SQLException e) {
            return "No connection with data base (";
        } catch (WrongPasswordException | NoSuchUserException e) {
            return e.getMessage();
        }
    }
}
