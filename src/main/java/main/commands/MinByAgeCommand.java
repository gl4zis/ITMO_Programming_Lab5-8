package main.commands;

import main.dragons.DragonCollection;

import java.io.Reader;

public class MinByAgeCommand extends NonArgsCommand {
    private MinByAgeCommand(String name) {
        super(name);
    }

    private static class CommandHolder {
        public static final Command INSTANCE = new MinByAgeCommand("min_by_age");
    }

    public static Command getInstance() {
        return CommandHolder.INSTANCE;
    }

    @Override
    public void execute() {
        System.out.println(DragonCollection.instance.minByAge());
    }
}