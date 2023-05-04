package commands;

import collection.DragonCollection;
import dragons.Dragon;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

/**
 * Argument command "filter_less_than_weight weight".
 * Output all dragons in collection, which weight less than inputted weight
 */
public class FilterLessThanWeightCommand extends Command {
    private final Logger LOGGER = LogManager.getLogger(FilterLessThanWeightCommand.class);
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    FilterLessThanWeightCommand(CommandManager manager) {
        super("filter_less_than_weight",
                "filter_less_than_weight weight : " +
                        "output the elements whose value of the weight field is less than the given one");
        this.collection = manager.getCollection();
    }

    /**
     * Output all dragons in collection, which weight less than inputted weight
     * If inputted arg is not number, outputs error message
     */
    @Override
    public String execute(Request request) {
        try {
            long weight = (long) request.arg();
            String output = collection.getItems().stream()
                    .filter(p -> p.getWeight() < weight).sorted(Dragon.coordComp).map(Dragon::toString).collect(Collectors.joining(
                            "\n----------------------------------------------------------------\n"));
            if (output.length() > 0) return output;
            else return "No such elements in collection";
        } catch (ClassCastException | NullPointerException e) {
            LOGGER.warn("Incorrect command argument");
            return "Incorrect command argument";
        }
    }
}