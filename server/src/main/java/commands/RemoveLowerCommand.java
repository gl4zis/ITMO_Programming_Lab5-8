package commands;

import collection.DragonCollection;
import dragons.Dragon;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Argument command "remove_lower {dragon}". Removes all dragons from collection, that less than inputted dragon
 */
public class RemoveLowerCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(RemoveLowerCommand.class);
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    RemoveLowerCommand(DragonCollection collection) {
        super("remove_lower",
                "remove_lower {dragon} : remove all items from the collection that are smaller than the specified");
        this.collection = collection;
    }

    /**
     * Removes all dragons from collection, that less than inputted dragon and output info about removed dragons.
     * If there are no such elements in collection, outputs error message
     */
    @Override
    public String execute(Request request) {
        Dragon minDragon = request.getDragon();
        StringBuilder output = new StringBuilder();
        collection.getItems().stream()
                .filter(p -> p.compareTo(minDragon) < 0).sorted(Dragon.coordComp).forEach(p -> {
                    collection.remove(p);
                    output.append("Removed dragon ").append(p.getName()).append(" with id: ").append(p.getId()).append('\n');
                });
        if (output.length() > 0) {
            output.deleteCharAt(-1);
            return output.toString();
        } else return "No such elements in collection";
    }
}