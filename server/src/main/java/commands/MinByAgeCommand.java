package commands;

import collection.DragonCollection;
import dragons.Dragon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.InputScriptReader;

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
        super("min_by_age",
                "min_by_age : вывести любой объект из коллекции, значение поля age которого является минимальным");
        this.collection = collection;
    }

    /**
     * Outputs one dragon from collection, which have minimum age or outputs message about empty collection
     *
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public String execute(InputScriptReader reader) {
        Dragon minDragon = collection.minByAge();
        LOGGER.debug("MinByAge command was successfully executed");
        if (minDragon == null) {
            return "Collection is empty";
        } else {
            return collection.minByAge().toString();
        }
    }
}