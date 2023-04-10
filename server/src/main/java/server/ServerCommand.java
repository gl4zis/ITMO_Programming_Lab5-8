package server;

import collection.CollectionManager;
import commands.CommandManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ServerCommand {
    private static final Logger LOGGER = LogManager.getLogger(ServerCommand.class);

    public static void help() {
        System.out.println("""
                exit : terminate server (collection will be saved)
                save : save collection to the file
                help : display help for available commands""");
    }

    public static void save(CommandManager manager) {
        CollectionManager.saveCollection(manager.getJson(), manager.getCollection());
    }

    public static void exit(CommandManager manager) {
        save(manager);
        System.exit(0);
    }
}
