package commands;

import client.ClientConnection;
import parsers.MyScanner;

/**
 * Abstract class of commands, which need to be processed on client
 */
public abstract class Command {

    protected final ClientConnection conn;
    private final String name;

    protected Command(ClientConnection connection, String name) {
        this.conn = connection;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String execute(String line, MyScanner reader);
}
