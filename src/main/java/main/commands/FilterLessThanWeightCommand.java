package main.commands;

import main.dragons.Dragon;
import main.dragons.DragonCollection;

import java.io.Reader;

public class FilterLessThanWeightCommand extends ArgsCommand {
    private FilterLessThanWeightCommand(String name) {
        super(name);
    }

    private static class CommandHolder {
        public static final Command INSTANCE = new FilterLessThanWeightCommand("filter_less_than_weight");
    }

    public static Command getInstance() {
        return CommandHolder.INSTANCE;
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