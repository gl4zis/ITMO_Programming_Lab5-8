package commands;

import collection.DragonCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Non-argument command "average_of_weight". Outputs average value of all dragon's weight in collection
 */
public class AverageOfWeightCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(AverageOfWeightCommand.class);
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    AverageOfWeightCommand(DragonCollection collection) {
        super("average_of_weight",
                "average_of_weight : вывести среднее значение поля weight для всех элементов коллекции");
        this.collection = collection;
    }

    /**
     * Output average value of all dragon's weight in collection
     */
    @Override
    public String execute() {
        LOGGER.debug("AverageOfWeight command was successfully executed");
        return "Average weight = " + collection.getAverageWeight();
    }
}