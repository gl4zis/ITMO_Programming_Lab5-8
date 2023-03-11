package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.exceptions.ObjectNotFoundException;
import org.application.lab5.parsers.InputScriptReader;

public class UpdateCommand extends ArgsCommand {

    private final DragonCollection collection;

    UpdateCommand(DragonCollection collection) {
        super("update", "update id {element} : обновить значение элемента коллекции, id которого равен заданному");
        this.collection = collection;
    }

    @Override
    public void execute(InputScriptReader reader, String arg) {
        try {
            int id = Integer.parseInt(arg);
            collection.remove(id);
            Dragon dragon;
            if (reader == null) {
                dragon = InputConsoleReader.readDragon();
            } else {
                dragon = reader.readDragon();
            }
            dragon.setId(id);
            collection.add(dragon);
            System.out.println("Новый объект успешно добавлен");
        } catch (NumberFormatException e) {
            System.out.println("Неверный аргумент");
        } catch (ObjectNotFoundException e) {
            System.out.println("Такого объекта нет в коллекции");
        }
    }
}