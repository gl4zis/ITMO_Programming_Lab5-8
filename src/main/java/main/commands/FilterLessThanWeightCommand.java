package main.commands;

import main.dragons.Dragon;
import main.dragons.DragonCollection;

import java.io.Reader;

public class FilterLessThanWeightCommand extends ArgsCommand {

    FilterLessThanWeightCommand() {
        super("filter_less_than_weight");
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