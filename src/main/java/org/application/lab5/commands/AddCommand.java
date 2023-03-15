package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.parsers.InputScriptReader;

/**
 * Non-argument command "add {dragon}". Adds new dragon object in collection.
 * Needs to type all non-auto-generated dragon vars after command
 */

public class AddCommand extends NonArgsCommand {
    private final DragonCollection collection;

    /** Constructor, sets collection, that the command works with, name and description of command
     */
    AddCommand(DragonCollection collection) {
        super("add", "add {dragon} : добавить новый элемент в коллекцию");
        this.collection = collection;
    }

    /** Reads all dragon vars, creates new dragon object and adds it in the collection
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public void execute(InputScriptReader reader) {
        Dragon dragon;
        if (reader == null) {
            dragon = InputConsoleReader.readDragon();
        } else {
            dragon = reader.readDragon();
        }
        collection.add(dragon);
        System.out.println("Новый объект успешно добавлен");
    }
}
