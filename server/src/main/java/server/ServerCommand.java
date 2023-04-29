package server;

import exceptions.ExitException;
import parsers.MyScanner;

/**
 * Utility for executing commands in server console
 */
public abstract class ServerCommand {

    /**
     * Checks console line and execute command if console is not empty
     */
    public static void execute() {
        String line = new MyScanner(System.in).checkConsole();
        if (line != null) {
            if ("exit".equals(line)) {
                throw new ExitException();
            } else {
                System.out.println("Unknown server command");
            }
        }
    }
}
