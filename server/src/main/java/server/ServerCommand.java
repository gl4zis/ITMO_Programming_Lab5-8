package server;

import collection.CollectionManager;
import commands.CommandManager;

/**
 * Utility for executing commands in server console
 */
public abstract class ServerCommand {

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
