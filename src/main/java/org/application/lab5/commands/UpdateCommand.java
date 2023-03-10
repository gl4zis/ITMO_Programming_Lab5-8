package org.application.lab5.commands;

import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.exceptions.ObjectNotFoundException;

public class UpdateCommand extends ArgsCommand {

    UpdateCommand() {
        super("update", "update id {element} : обновить значение элемента коллекции, id которого равен заданному");
    }

    @Override
    public void execute(String arg) {
        try {
            int id = Integer.parseInt(arg);
            DragonCollection.instance.remove(id);
            Dragon dragon = InputConsoleReader.readDragon();
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