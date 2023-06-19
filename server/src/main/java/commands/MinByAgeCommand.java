package commands;

import dragons.Dragon;
import dragons.DragonCollection;
import network.Request;

/**
 * Non-argument command "min_by_age". Outputs one dragon from collection, which have minimum age
 */
public class MinByAgeCommand extends Command {
    private final DragonCollection collection;

    /**
     * Constructor sets collection, that command works with, description of this command
     */
    MinByAgeCommand(CommandManager manager) {
        super("min_by_age : " +
                "output any object from the collection, the value of the age field of which is the minimum");
        this.collection = manager.getCollection();
    }

    /**
     * Outputs one dragon from collection, which have minimum age or message about empty collection
     */
    @Override
    public String execute(Request request) {
        Dragon minDragon = collection.minByAge();
        if (minDragon == null) {
            return "Collection is empty";
        } else {
            return collection.minByAge().toString();
        }
    }
}