package main.commands;

import main.dragons.DragonCollection;

import java.io.Reader;

public class InfoCommand extends NonArgsCommand {
    private InfoCommand(String name) {
        super(name);
    }

    private static class CommandHolder {
        public static final Command INSTANCE = new InfoCommand("info");
    }

    public static Command getInstance() {
        return CommandHolder.INSTANCE;
    }

    @Override
    public void execute() {
        System.out.println(DragonCollection.instance);
    }
}