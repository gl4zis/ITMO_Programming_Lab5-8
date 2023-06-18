package commands;

import GUI.MyConsole;
import client.ClientConnection;
import parsers.MyScanner;
import settings.Settings;

/**
 * Command 'update'
 */
public class UpdateCommand extends Command {
    UpdateCommand(Settings settings) {
        super(settings, "update");
    }

    /**
     * Sends 2 requests, 'find id' and then, if dragon exists 'update id'
     */
    @Override
    public void execute(MyConsole output) {
        /*String find = "find" + line.substring(6);
        String output = conn.sendReqGetResp(find, reader);
        if (!output.startsWith("No such")) {
            output = conn.sendReqGetResp(line, reader);
        }
        return output;
         */
    }
}
