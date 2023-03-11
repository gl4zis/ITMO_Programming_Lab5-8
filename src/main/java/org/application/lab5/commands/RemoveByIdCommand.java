package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.exceptions.ObjectNotFoundException;
import org.application.lab5.parsers.InputScriptReader;

public class RemoveByIdCommand extends ArgsCommand {

    private final DragonCollection collection;

    RemoveByIdCommand(DragonCollection collection) {
        super("remove_by_id", "remove_by_id id : удалить элемент из коллекции по его id");
        this.collection = collection;
    }

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
