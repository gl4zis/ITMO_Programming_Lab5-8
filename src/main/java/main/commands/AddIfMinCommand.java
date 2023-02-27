package main.commands;

import main.dragons.Dragon;
import main.dragons.DragonCollection;
import main.exceptions.IncorrectInputException;

import java.io.IOException;
import java.io.Reader;

public class AddIfMinCommand extends NonArgsCommand {
    private static AddIfMinCommand addIfMinCommand;

    private AddIfMinCommand(String name) {
        super(name);
    }

    public static AddIfMinCommand getInstance() {
        if (addIfMinCommand == null) addIfMinCommand = new AddIfMinCommand("add_if_min");
        return addIfMinCommand;
    }

    @Override
    public void scriptExecute(Reader reader) throws IncorrectInputException, IOException {
        Dragon dragon = readDragonFromScript(reader);
        Dragon minDragon = DragonCollection.instance.getMin();
        if (minDragon == null || DragonCollection.instance.getMin().compareTo(dragon) > 0) {
            DragonCollection.instance.add(dragon);
            System.out.println("Новый объект успешно добавлен");
        } else {
            Dragon.decUniqNumber();
            System.out.println("Объект не минимальный");
        }
    }

    @Override
    public void execute() {
        Dragon dragon = readDragon();
        Dragon minDragon = DragonCollection.instance.getMin();
        if (minDragon == null || DragonCollection.instance.getMin().compareTo(dragon) > 0) {
            DragonCollection.instance.add(dragon);
            System.out.println("Новый объект успешно добавлен");
        } else {
            Dragon.decUniqNumber();
            System.out.println("Объект не минимальный");
        }
    }
}
