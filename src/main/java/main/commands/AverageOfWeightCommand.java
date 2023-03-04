package main.commands;

import main.dragons.DragonCollection;

import java.io.Reader;

public class AverageOfWeightCommand extends NonArgsCommand {
    private AverageOfWeightCommand(String name) {
        super(name);
    }

    private static class CommandHolder {
        public static final Command INSTANCE = new AverageOfWeightCommand("average_of_weight");
    }

    public static Command getInstance() {
        return CommandHolder.INSTANCE;
    }

    @Override
    public void execute() {
        System.out.println(DragonCollection.instance.getAverageWeight());
    }
}