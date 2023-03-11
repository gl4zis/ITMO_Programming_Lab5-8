package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.parsers.InputScriptReader;

public class AddCommand extends NonArgsCommand {

    private final DragonCollection collection;

    AddCommand(DragonCollection collection) {
        super("add", "add {element} : добавить новый элемент в коллекцию");
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
        collection.add(dragon);
        System.out.println("Новый объект успешно добавлен");
    }
}
