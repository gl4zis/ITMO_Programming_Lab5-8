package main.commands;

import main.dragons.Dragon;
import main.dragons.DragonCollection;
import main.exceptions.IncorrectInputException;

import java.io.IOException;
import java.io.Reader;

public class AddCommand extends NonArgsCommand {

    private AddCommand(String name) {
        super(name);
    }

    private static class CommandHolder {
        public static final Command INSTANCE = new AddCommand("add");
    }

    public static Command getInstance() {
        return CommandHolder.INSTANCE;
    }

    @Override
    public void execute() {
        Dragon dragon = readDragon();
        DragonCollection.instance.add(dragon);
        System.out.println("Новый объект успешно добавлен");
    }
}
