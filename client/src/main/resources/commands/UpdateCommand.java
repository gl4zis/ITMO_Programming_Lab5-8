package commands;

import client.ClientConnection;
import parsers.MyScanner;

/**
 * Command 'update'
 */
public class UpdateCommand extends Command {
    UpdateCommand(ClientConnection connection) {
        super(connection, "update");
    }

    /**
     * Sends 2 requests, 'find id' and then, if dragon exists 'update id'
     *
     * @return server response
     */
    @Override
    public String execute(String line, MyScanner reader) {
        String find = "find" + line.substring(6);
        String output = conn.sendReqGetResp(find, reader);
        if (!output.startsWith("No such")) {
            output = conn.sendReqGetResp(line, reader);
        }
        return output;
    }
}
