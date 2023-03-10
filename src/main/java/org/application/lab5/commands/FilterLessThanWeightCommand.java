package org.application.lab5.commands;

import org.application.lab5.Main;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.parsers.InputScriptReader;

public class FilterLessThanWeightCommand extends ArgsCommand {

    FilterLessThanWeightCommand() {
        super("filter_less_than_weight",
                "filter_less_than_weight weight : вывести элементы, значение поля weight которых меньше заданного");
    }

    @Override
    public void execute(InputScriptReader reader, String arg) {
        try {
            long weight = Long.parseLong(arg);
            for (Dragon dragon : Main.DRAGON_COLLECTION.getItems()) {
                if (dragon.getWeight() < weight) System.out.println(dragon);
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный аргумент комманды");
        }
    }
}