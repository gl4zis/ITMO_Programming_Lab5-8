package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputScriptReader;

/**
 * Non-argument command "clear". Removes all dragons from the collection
 */

public class ClearCommand extends NonArgsCommand {
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    ClearCommand(DragonCollection collection) {
        super("clear", "clear : очистить коллекцию");
        this.collection = collection;
    }

    /**
     * Removes all dragons from the collection
     *
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public void execute(InputScriptReader reader) {
        collection.clear();
        System.out.println("Коллекция очищена");
    }
}
