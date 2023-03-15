package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputScriptReader;

/**
 * Non-argument command "average_of_weight". Outputs average value of all dragon's weight in collection
 */

public class AverageOfWeightCommand extends NonArgsCommand {
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    AverageOfWeightCommand(DragonCollection collection) {
        super("average_of_weight",
                "average_of_weight : вывести среднее значение поля weight для всех элементов коллекции");
        this.collection = collection;
    }

    /**
     * Output average value of all dragon's weight in collection
     *
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public void execute(InputScriptReader reader) {
        System.out.println(collection.getAverageWeight());
    }
}