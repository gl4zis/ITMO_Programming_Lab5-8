package main.commands;

import main.dragons.Dragon;
import main.dragons.DragonCollection;

import java.io.Reader;
import java.util.Iterator;

public class RemoveLowerCommand extends NonArgsCommand {
    private static RemoveLowerCommand removeLowerCommand;

    private RemoveLowerCommand(String name) {
        super(name);
    }

    public static RemoveLowerCommand getInstance() {
        if (removeLowerCommand == null) removeLowerCommand = new RemoveLowerCommand("remove_lower");
        return removeLowerCommand;
    }

    @Override
    public void scriptExecute(Reader reader) {
        //Реализовать
    }

    @Override
    public void execute() {
        Dragon minDragon = readDragon();
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