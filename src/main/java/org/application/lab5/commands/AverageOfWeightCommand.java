package org.application.lab5.commands;

import org.application.lab5.Main;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputScriptReader;

public class AverageOfWeightCommand extends NonArgsCommand {

    AverageOfWeightCommand() {
        super("average_of_weight",
                "average_of_weight : вывести среднее значение поля weight для всех элементов коллекции");
    }

    @Override
    public void execute(InputScriptReader reader) {
        System.out.println(Main.DRAGON_COLLECTION.getAverageWeight());
    }
}