package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.parsers.InputScriptReader;

/**
 * Argument command "filter_less_than_weight weight".
 * Output all dragons in collection, which weight less than inputted weight
 */

public class FilterLessThanWeightCommand extends ArgsCommand {
    private final DragonCollection collection;

    /** Constructor, sets collection, that the command works with, name and description of command
     */
    FilterLessThanWeightCommand(DragonCollection collection) {
        super("filter_less_than_weight",
                "filter_less_than_weight weight : вывести элементы, значение поля weight которых меньше заданного");
        this.collection = collection;
    }

    /** Output all dragons in collection, which weight less than inputted weight
     * If inputted arg is not number, outputs error message
     * @param reader reader of file from that gives data, if null data gives from console
     * @param arg weight, to compare dragon weights with it
     */
    @Override
    public void execute(InputScriptReader reader, String arg) {
        try {
            long weight = Long.parseLong(arg);
            int counter = 0;
            for (Dragon dragon : collection.getItems()) {
                if (dragon.getWeight() < weight) {
                    System.out.println(dragon);
                    counter++;
                }
            }
            if (counter == 0) System.out.println("Нет таких элементов в коллекции");
        } catch (NumberFormatException e) {
            System.out.println("Неверный аргумент комманды");
        }
    }
}