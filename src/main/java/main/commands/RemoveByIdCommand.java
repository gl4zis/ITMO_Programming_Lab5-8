package main.commands;

import main.dragons.DragonCollection;
import main.exceptions.ObjectNotFoundException;

import java.io.Reader;

public class RemoveByIdCommand extends ArgsCommand {

    RemoveByIdCommand() {
        super("remove_by_id", "remove_by_id id : удалить элемент из коллекции по его id");
    }

    @Override
    public void execute(String arg) {
        try {
            int id = Integer.parseInt(arg);
            DragonCollection.instance.remove(id);
            System.out.println("Обект успешно удалён");
        } catch (NumberFormatException e) {
            System.out.println("Неверный аргумент");
        } catch (ObjectNotFoundException e) {
            System.out.println("Такого объекта нет в коллекции");
        }
    }
}
