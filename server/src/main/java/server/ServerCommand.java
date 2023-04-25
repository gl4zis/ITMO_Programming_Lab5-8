package server;

import collection.CollectionManager;
import commands.CommandManager;
import exceptions.ExitException;
import parsers.MyScanner;

/**
 * Utility for executing commands in server console
 */
public abstract class ServerCommand {

    /**
     * Checks console line and execute command if console is not empty
     */
    public static void execute(CommandManager manager) {
        String line = new MyScanner(System.in).checkConsole();
        if (line != null) {
            switch (line) {
                case "help" -> ServerCommand.help();
                case "exit" -> {
                    ServerCommand.save(manager);
                    throw new ExitException();
                }
                case "save" -> ServerCommand.save(manager);
                default -> System.out.println("Unknown server command");
            }
        }
    }

    /**
     * Prints help about server commands
     */
    public static void help() {
        System.out.println("""
                exit : terminate server (collection will be saved)
                save : save collection to the file
                help : display help for available commands""");
    }

    /**
     * Saves collection to the JSON
     */
    public static void save(CommandManager manager) {
        CollectionManager.saveCollection(manager.getJson(), manager.getCollection());
    }
}
