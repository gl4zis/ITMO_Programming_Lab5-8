package main.commands;

import main.InputConsoleReader;
import main.dragons.Dragon;
import main.dragons.DragonCollection;

import java.io.Reader;
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