package main.commands;

import main.dragons.DragonCollection;

import java.io.Reader;

public class MinByAgeCommand extends NonArgsCommand {
    private static MinByAgeCommand minByAgeCommand;

    private MinByAgeCommand(String name) {
        super(name);
    }

    public static MinByAgeCommand getInstance() {
        if (minByAgeCommand == null) minByAgeCommand = new MinByAgeCommand("min_by_age");
        return minByAgeCommand;
    }

    @Override
    public void execute() {
        System.out.println(DragonCollection.instance.minByAge());
    }
}