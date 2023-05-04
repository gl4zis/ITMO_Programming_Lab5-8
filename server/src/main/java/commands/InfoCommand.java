package commands;

import collection.DragonCollection;
import network.Request;

/**
 * Non-argument command "info". Outputs info about collection, that application works with
 */
public class InfoCommand extends Command {
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    InfoCommand(CommandManager manager) {
        super("info", "info : display information about the collection");
        this.collection = manager.getCollection();
    }

    /**
     * Outputs info about collection, that application works with
     * (Size, type, date of creation and maximum dragon id)
     */
    @Override
    public String execute(Request request) {
        return collection.toString();
    }
}