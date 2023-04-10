package commands;

import collection.DragonCollection;
import dragons.Dragon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


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
        super("show", "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collection = collection;
    }

    /**
     * Outputs info about all elements in the collection or outputs message about empty collection
     */
    @Override
    public String execute() {
        if (collection.getItems().size() == 0) {
            LOGGER.debug("Show command was successfully executed");
            return "Collection is empty";
        } else {
            int counter = 0;
            StringBuilder line = new StringBuilder();
            for (Dragon dragon : collection.getItems()) {
                if (counter >= 1)
                    line.append("----------------------------------------------------------------\n");
                line.append(dragon);
                if (counter < collection.getItems().size() - 1)
                    line.append("\n");
                counter++;
            }
            LOGGER.debug("Show command was successfully executed");
            return line.toString();
        }
    }
}