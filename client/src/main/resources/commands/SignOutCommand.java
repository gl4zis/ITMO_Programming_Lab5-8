package commands;

import client.ClientConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.MyScanner;

/**
 * Command 'sign_out'
 */
public class SignOutCommand extends Command {

    private static final Logger LOGGER = LogManager.getLogger(SignOutCommand.class);

    SignOutCommand(ClientConnection connection) {
        super(connection, "sign_out");
    }

    /**
     * Sets new current user
     *
     * @return empty line
     */
    @Override
    public String execute(String line, MyScanner reader) {
        LOGGER.info("User was signed out");
        conn.getSettings().setUser(null);
        conn.setUser();
        return "";
    }
}
