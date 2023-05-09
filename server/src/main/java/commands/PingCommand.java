package commands;

import network.Request;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Non-argument command 'ping'.
 * Just for checking connection to server
 */
public class PingCommand extends Command {

    /**
     * Constructor sets description of command
     */
    PingCommand(CommandManager manager) {
        super("ping : show information about connection to server");
    }

    /**
     * Outputs information about connection between client and server
     */
    @Override
    public String execute(Request request) {
        InetAddress host;
        try {
            host = InetAddress.getLocalHost();
            return "Connected to server: " + host;
        } catch (UnknownHostException ignored) {
        }
        return "Connected to server";
    }
}
