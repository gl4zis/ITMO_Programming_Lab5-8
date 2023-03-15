package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.exceptions.ObjectNotFoundException;
import org.application.lab5.parsers.InputScriptReader;

/**
 * Argument command "remove_by_id id". Removes dragon from the collection by its id
 */

public class RemoveByIdCommand extends ArgsCommand {
    private final DragonCollection collection;

    /** Constructor, sets collection, that the command works with, name and description of command
     */
    RemoveByIdCommand(DragonCollection collection) {
        super("remove_by_id", "remove_by_id id : удалить элемент из коллекции по его id");
        this.collection = collection;
    }

    /** Removes dragon from the collection by its id.
     * If there is incorrect argument or there is no dragon with such id in collection, outputs error messages
     * @param reader reader of file from that gives data, if null data gives from console
     * @param arg id of dragon, which you want to remove
     */
    @Override
    public void execute(InputScriptReader reader, String arg) {
        try {
            int id = Integer.parseInt(arg);
            collection.remove(id);
            System.out.println("Обект успешно удалён");
        } catch (NumberFormatException e) {
            System.out.println("Неверный аргумент");
        } catch (ObjectNotFoundException e) {
            System.out.println("Такого объекта нет в коллекции");
        }
    }
}
