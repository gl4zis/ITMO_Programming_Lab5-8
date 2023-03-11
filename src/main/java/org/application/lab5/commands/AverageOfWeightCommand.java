package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputScriptReader;

public class AverageOfWeightCommand extends NonArgsCommand {

    private final DragonCollection collection;

    AverageOfWeightCommand(DragonCollection collection) {
        super("average_of_weight",
                "average_of_weight : вывести среднее значение поля weight для всех элементов коллекции");
        this.collection = collection;
    }

    @Override
    public void execute(InputScriptReader reader) {
        System.out.println(collection.getAverageWeight());
    }
}