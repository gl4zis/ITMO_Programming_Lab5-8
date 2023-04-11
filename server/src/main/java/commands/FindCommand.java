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
     * Standard constructor
     */
    FindCommand(DragonCollection collection) {
        super("find", "find id : returns dragon from collection by its id");
        this.collection = collection;
    }

    /**
     * Returns dragon with inputted id or 'No such element in collection'
     */
    @Override
    public String execute(Request request) {
        Dragon dragon = collection.find((int) request.getArg());
        if (dragon != null) return dragon.toString();
        else return "No such element in collection";
    }
}
