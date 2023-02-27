package main.commands;

import main.dragons.DragonCollection;

import java.io.Reader;

public class AverageOfWeightCommand extends NonArgsCommand {
    private static AverageOfWeightCommand averageOfWeightCommand;

    private AverageOfWeightCommand(String name) {
        super(name);
    }

    public static AverageOfWeightCommand getInstance() {
        if (averageOfWeightCommand == null) averageOfWeightCommand = new AverageOfWeightCommand("average_of_weight");
        return averageOfWeightCommand;
    }

    @Override
    public void scriptExecute(Reader reader) {
        execute();
    }

    @Override
    public void execute() {
        System.out.println(DragonCollection.instance.getAverageWeight());
    }
}