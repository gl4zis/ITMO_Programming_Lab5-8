package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.parsers.InputScriptReader;

import java.util.Iterator;

public class RemoveLowerCommand extends NonArgsCommand {

    private final DragonCollection collection;

    RemoveLowerCommand(DragonCollection collection) {
        super("remove_lower",
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный");
        this.collection = collection;
    }

    @Override
    public void execute(InputScriptReader reader) {
        Dragon minDragon;
        if (reader == null) {
            minDragon = InputConsoleReader.readDragon();
        } else {
            minDragon = reader.readDragon();
        }
        Iterator<Dragon> iterator = collection.getItems().iterator();
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