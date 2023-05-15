package commands;

import client.ClientConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.MyScanner;
import user.User;

/**
 * Command 'change_password password'
 */
public class ChangePasswordCommand extends Command {

    private static final Logger LOGGER = LogManager.getLogger(ChangePasswordCommand.class);

    ChangePasswordCommand(ClientConnection connection) {
        super(connection, "change_password");
    }

    /**
     * Hashes password from line and requests to change it
     *
     * @return server response
     */
    @Override
    public String execute(String line, MyScanner reader) {
        try {
            String passwd = line.split(" ")[1];
            passwd = User.hashPasswd(passwd, 500);
            return conn.sendReqGetResp("change_password " + passwd, reader);
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.warn("Incorrect input. Unknown command");
            return "";
        }
    }
}
