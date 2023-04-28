package commands;

import collection.DragonCollection;
import network.Request;

/**
 * Non-argument command "clear". Removes all dragons from the collection
 */
public class ClearCommand extends Command {
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    ClearCommand(DragonCollection collection) {
        super("clear", "clear : clear the collection");
        this.collection = collection;
    }

    /**
     * Removes all dragons from the collection
     */
    @Override
    public String execute(Request request) {
        collection.clear();
        return "Collection was cleared";
    }
}
