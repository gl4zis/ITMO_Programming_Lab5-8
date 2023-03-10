package org.application.lab5.commands;

import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.dragons.Dragon;

import java.util.Iterator;

public class RemoveLowerCommand extends NonArgsCommand {

    RemoveLowerCommand() {
        super("remove_lower",
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный");
    }

    @Override
    public void execute() {
        Dragon minDragon = InputConsoleReader.readDragon();
        Iterator<Dragon> iterator = DragonCollection.instance.getItems().iterator();
        Dragon dragon;
        while (iterator.hasNext()) {
            dragon = iterator.next();
            if (dragon.compareTo(minDragon) < 0) {
                iterator.remove();
                System.out.println("Обект с id " + dragon.getId() + ", " + dragon.getName() + " был удален");
            }
        }
    }
}