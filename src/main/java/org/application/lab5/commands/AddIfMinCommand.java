package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.parsers.InputScriptReader;

/**
 * Non-argument command "add_if_min {dragon}"
 */

public class AddIfMinCommand extends NonArgsCommand {
    private final DragonCollection collection;

    /** Constructor, sets collection, that the command works with, name and description of command
     */
    AddIfMinCommand(DragonCollection collection) {
        super("add_if_min",
                "add_if_min {dragon} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.collection = collection;
    }

    /** Reads all dragon vars, creates new dragon object and adds it in the collection if there are no dragons less that this
     * @param reader  reader of file from that gives data, if null data gives from console
     */
    @Override
    public void execute(InputScriptReader reader) {
        Dragon dragon;
        if (reader == null) {
            dragon = InputConsoleReader.readDragon();
        } else {
            dragon = reader.readDragon();
        }
        Dragon minDragon = collection.getMinDragon();
        if (minDragon == null || collection.getMinDragon().compareTo(dragon) > 0) {
            collection.add(dragon);
            System.out.println("Новый объект успешно добавлен");
        } else {
            Dragon.decUniqNumber();
            System.out.println("Объект не минимальный");
        }
    }
}
