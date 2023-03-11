package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.parsers.InputScriptReader;

import java.util.Iterator;

public class RemoveGreaterCommand extends NonArgsCommand {

    private final DragonCollection collection;

    RemoveGreaterCommand(DragonCollection collection) {
        super("remove_greater",
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный");
        this.collection = collection;
    }

    @Override
    public void execute(InputScriptReader reader) {
        Dragon maxDragon;
        if (reader == null) {
            maxDragon = InputConsoleReader.readDragon();
        } else {
            maxDragon = reader.readDragon();
        }
        Iterator<Dragon> iterator = collection.getItems().iterator();
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