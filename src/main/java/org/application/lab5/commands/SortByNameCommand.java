package org.application.lab5.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputScriptReader;

/**
 * Non-argument command "sort_by_name". Sorts collection elements by name and id
 */
public class SortByNameCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(SortByNameCommand.class);
    private final DragonCollection collection;

    /** Constructor, sets collection, that the command works with, name and description of command
     */
    SortByNameCommand(DragonCollection collection) {
        super("sort_by_name", "sort_by_name : отсортировать коллекцию по имени (если имена одинаковые, то по Id)");
        this.collection = collection;
    }

    /** Sorts collection elements by name and id or outputs message about empty collection
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public void execute(InputScriptReader reader) {
        if (collection.getItems().size() > 0) {
            collection.sort();
            LOGGER.info("Collection was sorted");
        } else System.out.println("Collection is empty");
        LOGGER.debug("SortByName command was successfully executed");
    }
}
