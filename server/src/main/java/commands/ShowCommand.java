package commands;

import collection.DragonCollection;
import dragons.Dragon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;


/**
 * Non-argument command "show". Outputs info about all elements in the collection
 */
public class ShowCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(ShowCommand.class);
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
    public String execute() {
        if (collection.getItems().size() == 0) {
            return "Collection is empty";
        } else {
            int counter = 0;
            StringBuilder output = new StringBuilder();
            for (Dragon dragon : collection.sorted()) {
                if (counter >= 1)
                    output.append("----------------------------------------------------------------\n");
                output.append(dragon).append('\n');
            }
            output.deleteCharAt(output.length() - 1);
            return output.toString();
        }
    }
}