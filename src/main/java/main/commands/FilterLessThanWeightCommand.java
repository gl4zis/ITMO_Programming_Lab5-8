package main.commands;

import main.dragons.Dragon;
import main.dragons.DragonCollection;

import java.io.Reader;

public class FilterLessThanWeightCommand extends ArgsCommand {
    private static FilterLessThanWeightCommand filterLessThanWeightCommand;

    private FilterLessThanWeightCommand(String name) {
        super(name);
    }

    public static FilterLessThanWeightCommand getInstance() {
        if (filterLessThanWeightCommand == null)
            filterLessThanWeightCommand = new FilterLessThanWeightCommand("filter_less_than_weight");
        return filterLessThanWeightCommand;
    }

    @Override
    public void scriptExecute(Reader reader, String arg) {
        execute(arg);
    }

    @Override
    public void execute(String arg) {
        try {
            long weight = Long.parseLong(arg);
            for (Dragon dragon : DragonCollection.instance.getItems()) {
                if (dragon.getWeight() < weight) System.out.println(dragon);
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный аргумент комманды");
        }
    }
}