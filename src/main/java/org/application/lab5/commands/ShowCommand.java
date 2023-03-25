package org.application.lab5.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.parsers.InputScriptReader;


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
     *
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public void execute(InputScriptReader reader) {
        if (collection.getItems().size() == 0) {
            System.out.println("Collection is empty");
        } else {
            int counter = 0;
            for (Dragon dragon : collection.getItems()) {
                if (counter >= 1)
                    System.out.println("----------------------------------------------------------------");
                System.out.println(dragon);
                counter++;
            }
        }
        LOGGER.debug("Show command was successfully executed");
    }
}