package main.commands;

import main.dragons.DragonCollection;
import main.exceptions.ObjectNotFoundException;

import java.io.Reader;

public class RemoveByIdCommand extends ArgsCommand {
    private static RemoveByIdCommand removeByIdCommand;

    private RemoveByIdCommand(String name) {
        super(name);
    }

    public static RemoveByIdCommand getInstance() {
        if (removeByIdCommand == null) removeByIdCommand = new RemoveByIdCommand("remove_by_id");
        return removeByIdCommand;
    }

    @Override
    public void scriptExecute(Reader reader, String arg) {
        execute(arg);
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
