package main.commands;

import main.dragons.Dragon;
import main.dragons.DragonCollection;

import java.io.Reader;
import java.util.Iterator;

public class RemoveGreaterCommand extends NonArgsCommand {
    private static RemoveGreaterCommand removeGreaterCommand;

    private RemoveGreaterCommand(String name) {
        super(name);
    }

    public static RemoveGreaterCommand getInstance() {
        if (removeGreaterCommand == null) removeGreaterCommand = new RemoveGreaterCommand("remove_greater");
        return removeGreaterCommand;
    }

    @Override
    public void scriptExecute(Reader reader) {
        //Реализовать
    }

    @Override
    public void execute() {
        Dragon maxDragon = readDragon();
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