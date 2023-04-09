package server;


import collection.CollectionManager;
import collection.DragonCollection;
import commands.CommandManager;
import parsers.JsonManager;

public class Main {
    public static void main(String[] args) {
        try {
            JsonManager jsonManager;
            if (args.length == 0) {
                jsonManager = new JsonManager("");
            } else jsonManager = new JsonManager(args[0]);
            DragonCollection collection = new DragonCollection();
            CommandManager commandManager = new CommandManager(jsonManager, collection);
            CollectionManager.uploadCollection(jsonManager.readJSON(), collection);
            Connection con = new Connection(6789, commandManager);
            con.open();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
