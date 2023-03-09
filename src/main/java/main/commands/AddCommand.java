package main.commands;

import main.InputConsoleReader;
import main.dragons.Dragon;
import main.dragons.DragonCollection;

public class AddCommand extends NonArgsCommand {


    AddCommand() {
        super("add", "add {element} : добавить новый элемент в коллекцию");
    }

    @Override
    public void execute() {
        Dragon dragon = InputConsoleReader.readDragon();
        DragonCollection.instance.add(dragon);
        System.out.println("Новый объект успешно добавлен");
    }
}
