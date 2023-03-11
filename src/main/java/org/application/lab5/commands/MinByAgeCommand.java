package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputScriptReader;

public class MinByAgeCommand extends NonArgsCommand {

    private final DragonCollection collection;

    MinByAgeCommand(DragonCollection collection) {
        super("min_by_age",
                "min_by_age : вывести любой объект из коллекции, значение поля age которого является минимальным");
        this.collection = collection;
    }

    @Override
    public void execute(InputScriptReader reader) {
        System.out.println(collection.minByAge());
    }
}