package main.commands;

import main.dragons.Dragon;
import main.dragons.DragonCollection;
import main.exceptions.IncorrectInputException;

import java.io.IOException;
import java.io.Reader;

public class AddCommand extends NonArgsCommand {
    private static AddCommand addCommand;

    private AddCommand(String name) {
        super(name);
    }

    public static AddCommand getInstance() {
        if (addCommand == null) addCommand = new AddCommand("add");
        return addCommand;
    }

    @Override
    public void scriptExecute(Reader reader) throws IOException, IncorrectInputException {
        Dragon dragon = Command.readDragonFromScript(reader);
        DragonCollection.instance.add(dragon);
        System.out.println("Новый объект успешно добавлен");
    }

    @Override
    public void execute() {
        Dragon dragon = readDragon();
        DragonCollection.instance.add(dragon);
        System.out.println("Новый объект успешно добавлен");
    }
}
