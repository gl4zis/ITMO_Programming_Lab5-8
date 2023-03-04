package main.commands;

import main.dragons.DragonCollection;

import java.io.Reader;

public class AverageOfWeightCommand extends NonArgsCommand {

    AverageOfWeightCommand() {
        super("average_of_weight");
    }

    @Override
    public void execute() {
        System.out.println(DragonCollection.instance.getAverageWeight());
    }
}