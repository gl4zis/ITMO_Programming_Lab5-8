package main.commands;

import main.dragons.DragonCollection;

import java.io.Reader;

public class ClearCommand extends NonArgsCommand {
    private ClearCommand(String name) {
        super(name);
    }

    private static class CommandHolder {
        public static final Command INSTANCE = new ClearCommand("clear");
    }

    public static Command getInstance() {
        return CommandHolder.INSTANCE;
    }

    @Override
    public void execute() {
        DragonCollection.instance.clear();
        System.out.println("Коллекция очищена");
    }
}
