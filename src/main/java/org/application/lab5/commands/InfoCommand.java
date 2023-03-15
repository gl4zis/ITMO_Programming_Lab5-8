package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputScriptReader;

/**
 * Non-argument command "info". Outputs info about collection, that application works with
 */

public class InfoCommand extends NonArgsCommand {
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    InfoCommand(DragonCollection collection) {
        super("info", "info : вывести в стандартный поток вывода информацию о коллекции");
        this.collection = collection;
    }

    /**
     * Outputs info about collection, that application works with
     * (Size, type, date of creation and maximum dragon id)
     *
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public void execute(InputScriptReader reader) {
        System.out.println(collection);
    }
}