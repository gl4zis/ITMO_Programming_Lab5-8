package main.commands;

import main.dragons.Dragon;
import main.dragons.DragonCollection;
import main.exceptions.ObjectNotFoundException;

import java.io.Reader;

public class UpdateCommand extends ArgsCommand {
    private static UpdateCommand updateCommand;

    private UpdateCommand(String name) {
        super(name);
    }

    public static UpdateCommand getInstance() {
        if (updateCommand == null) updateCommand = new UpdateCommand("update");
        return updateCommand;
    }

    @Override
    public void scriptExecute(Reader reader, String arg) {
        //Реализовать
    }

    @Override
    public void execute(String arg) {
        try {
            int id = Integer.parseInt(arg);
            DragonCollection.instance.remove(id);
            Dragon dragon = Command.readDragon();
            dragon.setId(id);
            DragonCollection.instance.add(dragon);
            System.out.println("Новый объект успешно добавлен");
        } catch (NumberFormatException e) {
            System.out.println("Неверный аргумент");
        } catch (ObjectNotFoundException e) {
            System.out.println("Такого объекта нет в коллекции");
        }
    }
}