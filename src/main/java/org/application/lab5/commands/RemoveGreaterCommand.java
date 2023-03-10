package org.application.lab5.commands;

import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.dragons.Dragon;

import java.util.Iterator;

public class RemoveGreaterCommand extends NonArgsCommand {

    RemoveGreaterCommand() {
        super("remove_greater",
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный");
    }

    @Override
    public void execute() {
        Dragon maxDragon = InputConsoleReader.readDragon();
        Iterator<Dragon> iterator = DragonCollection.instance.getItems().iterator();
        Dragon dragon;
        while (iterator.hasNext()) {
            dragon = iterator.next();
            if (dragon.compareTo(maxDragon) > 0) {
                iterator.remove();
                System.out.println("Обект с id " + dragon.getId() + ", " + dragon.getName() + " был удален");
            }
        }
    }
}