package commands;

import collection.DragonCollection;
import dragons.Dragon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * Argument command "filter_less_than_weight weight".
 * Output all dragons in collection, which weight less than inputted weight
 */
public class FilterLessThanWeightCommand extends ArgsCommand {
    private final Logger LOGGER = LogManager.getLogger(FilterLessThanWeightCommand.class);
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    FilterLessThanWeightCommand(DragonCollection collection) {
        super("filter_less_than_weight",
                "filter_less_than_weight weight : " +
                        "output the elements whose value of the weight field is less than the given one");
        this.collection = collection;
    }

    /**
     * Output all dragons in collection, which weight less than inputted weight
     * If inputted arg is not number, outputs error message
     *
     * @param arg weight, to compare dragon weights with it
     */
    @Override
    public String execute(String arg) {
        try {
            long weight = Long.parseLong(arg);
            int counter = 0;
            StringBuilder output = new StringBuilder();
            for (Dragon dragon : collection.getItems()) {
                if (dragon.getWeight() < weight) {
                    if (++counter > 1)
                        output.append("----------------------------------------------------------------\n");
                    output.append(dragon).append('\n');
                }
            }
            if (counter == 0) {
                return "No such elements in collection";
            } else {
                output.deleteCharAt(output.length() - 1);
                return output.toString();
            }
        } catch (NumberFormatException e) {
            LOGGER.warn("Incorrect command argument");
            return "Incorrect command argument";
        }
    }
}