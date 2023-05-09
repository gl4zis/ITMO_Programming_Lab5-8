package commands;

import collection.DragonCollection;
import dragons.Dragon;
import network.Request;

/**
 * Argument command "find id".
 * Returns dragon with inputted id
 */
public class FindCommand extends Command {
    private final DragonCollection collection;

    /**
     * Constructor sets collection, that the command works with, description of command
     */
    FindCommand(CommandManager manager) {
        super("find id : returns dragon from collection by its id");
        this.collection = manager.getCollection();
    }

    /**
     * Returns dragon with inputted id or 'No such element in collection' if there are no dragon with this id
     */
    @Override
    public String execute(Request request) {
        Dragon dragon = collection.find((int) request.arg());
        if (dragon != null) return dragon.toString();
        else return "No such element in collection";
    }
}
