package commands;

import client.ClientConnection;
import parsers.MyScanner;

import java.util.Date;

/**
 * Command 'ping'
 */
public class PingCommand extends Command {
    PingCommand(ClientConnection connection) {
        super(connection, "ping");
    }

    /**
     * Gets info about connection with server
     *
     * @return server response + average reply time
     */
    @Override
    public String execute(String line, MyScanner reader) {
        long startTime = new Date().getTime();
        String output = "";
        for (int i = 0; i < 10; i++) {
            output = conn.sendReqGetResp("ping", reader);
        }
        long endTime = new Date().getTime() - startTime;
        return output + "\nAverage reply time: " + endTime / 10 + " ms";
    }
}
