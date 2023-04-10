package server;


import collection.CollectionManager;
import collection.DragonCollection;
import commands.CommandManager;
import parsers.JsonManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    // Sets system properties for logger configuration and for saving log files
    static {
        String creationDate = new SimpleDateFormat("yyyy-MM-dd/HH-mm-ss").format(new Date());
        System.setProperty("logs.path", "./lab5-8-server-logs/" + creationDate + ".log");
    }

    public static void main(String[] args) {
        try {
            JsonManager jsonManager;
            if (args.length == 0) {
                jsonManager = new JsonManager("");
            } else jsonManager = new JsonManager(args[0]);
            DragonCollection collection = new DragonCollection();
            CommandManager commandManager = new CommandManager(jsonManager, collection);
            CollectionManager.uploadCollection(jsonManager.readJSON(), collection);
            Connection con = new Connection(commandManager);
            con.open(6789);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
