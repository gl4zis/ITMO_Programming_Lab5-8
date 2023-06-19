package commands;

import dragons.Dragon;
import dragons.DragonCollection;
import network.Request;

import java.util.stream.Collectors;

/**
 * Argument command "filter_less_than_weight weight".
 * Output all dragons in collection, which weight less than inputted weight
 */
public class FilterLessThanWeightCommand extends Command {
    private final DragonCollection collection;

    /**
     * Constructor sets collection, that the command works with, description of command
     */
    FilterLessThanWeightCommand(CommandManager manager) {
        super("filter_less_than_weight weight : " +
                "output the elements whose value of the weight field is less than the given one");
        this.collection = manager.getCollection();
    }

    /**
     * Output all dragons in collection, which weight less than inputted weight
     */
    @Override
    public String execute(Request request) {
        long weight = (long) request.arg();
        String output = collection.getItems().stream()
                .filter(p -> p.getWeight() < weight).sorted(Dragon.coordComp).map(Dragon::toString).collect(Collectors.joining(
                        "\n----------------------------------------------------------------\n"));
        if (output.length() > 0) return output;
        else return "No such elements in collection";
    }
}