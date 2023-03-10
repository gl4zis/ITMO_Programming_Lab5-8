package org.application.lab5.commands;

import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.dragons.Dragon;

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
