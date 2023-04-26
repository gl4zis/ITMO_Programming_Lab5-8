package commands;

import collection.DragonCollection;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Non-argument command "average_of_weight". Outputs average value of all dragon's weight in collection
 */
public class AverageOfWeightCommand extends Command {
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    AverageOfWeightCommand(DragonCollection collection) {
        super("average_of_weight",
                "average_of_weight : display the average value of the weight field for all items in the collection");
        this.collection = collection;
    }

    /**
     * Output average value of all dragon's weight in collection
     */
    @Override
    public String execute(Request request) {
        return "Average weight = " + collection.getAverageWeight();
    }
}