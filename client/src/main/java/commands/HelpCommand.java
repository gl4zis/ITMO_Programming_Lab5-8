package commands;

import client.ClientConnection;
import parsers.MyScanner;

/**
 * Command 'help'
 */
public class HelpCommand extends Command {

    HelpCommand(ClientConnection connection) {
        super(connection, "help");
    }

    /**
     * Returns info about all commands
     *
     * @return server response
     */
    @Override
    public String execute(String line, MyScanner reader) {
        String output = conn.sendReqGetResp("help", reader);
        output += """
                        
                \tsign_out : sign out from the account
                \texit : terminate the program
                \texecute_script filepath : execute script in the file, by its filepath""";
        return output;
    }
}
