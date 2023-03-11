package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.parsers.InputScriptReader;

public class AddIfMinCommand extends NonArgsCommand {

    private final DragonCollection collection;

    AddIfMinCommand(DragonCollection collection) {
        super("add_if_min",
                "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.collection = collection;
    }

    @Override
    public void execute(InputScriptReader reader) {
        Dragon dragon;
        if (reader == null) {
            dragon = InputConsoleReader.readDragon();
        } else {
            dragon = reader.readDragon();
        }
        Dragon minDragon = collection.getMin();
        if (minDragon == null || collection.getMin().compareTo(dragon) > 0) {
            collection.add(dragon);
            System.out.println("Новый объект успешно добавлен");
        } else {
            Dragon.decUniqNumber();
            System.out.println("Объект не минимальный");
        }
    }
}
