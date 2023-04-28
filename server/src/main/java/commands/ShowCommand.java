package commands;

import collection.DragonCollection;
import dragons.Dragon;
import network.Request;

import java.util.stream.Collectors;


/**
 * Non-argument command "show". Outputs info about all elements in the collection
 */
public class ShowCommand extends Command {
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    ShowCommand(DragonCollection collection) {
        super("show", "show : " +
                "output all elements of the collection in string representation");
        this.collection = collection;
    }

    /**
     * Outputs info about all elements in the collection or outputs message about empty collection
     */
    @Override
    public String execute(Request request) {
        String output = collection.getItems().stream()
                .sorted(Dragon.coordComp).map(Dragon::toString).collect(Collectors.joining(
                        "\n----------------------------------------------------------------\n"));
        if (output.length() > 0) return output;
        else return "No such elements in collection";
    }
}