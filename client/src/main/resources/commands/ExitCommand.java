package commands;

import client.ClientConnection;
import exceptions.ExitException;
import parsers.MyScanner;

/**
 * Command 'exit'. (exiting from client)
 */
public class ExitCommand extends Command {
    ExitCommand(ClientConnection connection) {
        super(connection, "exit");
    }

    /**
     * @throws ExitException always
     */
    @Override
    public String execute(String line, MyScanner reader) {
        throw new ExitException();
    }
}
