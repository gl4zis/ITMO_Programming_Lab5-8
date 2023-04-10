package commands;

import collection.DragonCollection;
import dragons.Dragon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Non-argument command "min_by_age". Outputs one dragon from collection, which have minimum age
 */
public class MinByAgeCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(MinByAgeCommand.class);
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that command works with, name and description of this command
     */
    MinByAgeCommand(DragonCollection collection) {
        super("min_by_age", "min_by_age : " +
                "output any object from the collection, the value of the age field of which is the minimum");
        this.collection = collection;
    }

    /**
     * Outputs one dragon from collection, which have minimum age or outputs message about empty collection
     */
    @Override
    public String execute() {
        Dragon minDragon = collection.minByAge();
        if (minDragon == null) {
            return "Collection is empty";
        } else {
            return collection.minByAge().toString();
        }
    }
}