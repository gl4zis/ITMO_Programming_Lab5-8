package commands;

import dragons.Dragon;
import dragons.DragonCollection;
import network.Request;

import java.util.stream.Collectors;


/**
 * Non-argument command "show".
 * Outputs info about all elements in the collection
 */
public class ShowCommand extends Command {
    private final DragonCollection collection;

    /**
     * Constructor sets collection, that the command works with, description of command
     */
    ShowCommand(CommandManager manager) {
        super("show : " +
                "output all elements of the collection in string representation");
        this.collection = manager.getCollection();
    }

    /**
     * Outputs info about all elements in the collection or message about empty collection
     */
    @Override
    public String execute(Request request) {
        String output = collection.getItems().stream()
                .sorted(Dragon.coordComp).map(Dragon::toString).collect(Collectors.joining(
                        "\n----------------------------------------------------------------\n"));
        if (output.length() > 0) return output;
        else return "Collection is empty";
    }
}