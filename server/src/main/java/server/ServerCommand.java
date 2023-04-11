package server;

import collection.CollectionManager;
import commands.CommandManager;

public abstract class ServerCommand {
    public static void help() {
        System.out.println("""
                exit : terminate server (collection will be saved)
                save : save collection to the file
                help : display help for available commands""");
    }

    public static void save(CommandManager manager) {
        CollectionManager.saveCollection(manager.getJson(), manager.getCollection());
    }
}
