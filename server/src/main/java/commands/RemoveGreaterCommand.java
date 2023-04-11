package commands;

import collection.DragonCollection;
import dragons.Dragon;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Argument command "remove_greater {dragon}". Removes all dragons from collection, that more than inputted dragon
 */
public class RemoveGreaterCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(RemoveGreaterCommand.class);
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    RemoveGreaterCommand(DragonCollection collection) {
        super("remove_greater",
                "remove_greater {dragon} : remove from the collection all items exceeding the specified");
        this.collection = collection;
    }

    /**
     * Removes all dragons from collection, that more than inputted dragon and output info about removed dragons.
     * If there are no such elements in collection, outputs error message
     */
    @Override
    public String execute(Request request) {
        Dragon maxDragon = request.getDragon();
        StringBuilder output = new StringBuilder();
        collection.getItems().stream()
                .filter(p -> p.compareTo(maxDragon) > 0).sorted(Dragon.coordComp).forEach(p -> {
                    collection.remove(p);
                    output.append("Removed dragon ").append(p.getName()).append(" with id: ").append(p.getId()).append('\n');
                });
        if (output.length() > 0) {
            output.deleteCharAt(-1);
            return output.toString();
        } else return "No such elements in collection";
    }
}