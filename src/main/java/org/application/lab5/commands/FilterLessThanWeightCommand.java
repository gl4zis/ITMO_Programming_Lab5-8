package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.parsers.InputScriptReader;

public class FilterLessThanWeightCommand extends ArgsCommand {

    private final DragonCollection collection;

    FilterLessThanWeightCommand(DragonCollection collection) {
        super("filter_less_than_weight",
                "filter_less_than_weight weight : вывести элементы, значение поля weight которых меньше заданного");
        this.collection = collection;
    }

    @Override
    public void execute(InputScriptReader reader, String arg) {
        try {
            long weight = Long.parseLong(arg);
            for (Dragon dragon : collection.getItems()) {
                if (dragon.getWeight() < weight) System.out.println(dragon);
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный аргумент комманды");
        }
    }
}