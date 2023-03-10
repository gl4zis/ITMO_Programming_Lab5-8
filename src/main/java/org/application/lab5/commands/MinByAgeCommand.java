package org.application.lab5.commands;

import org.application.lab5.Main;
import org.application.lab5.collection.DragonCollection;

public class MinByAgeCommand extends NonArgsCommand {

    MinByAgeCommand() {
        super("min_by_age",
                "min_by_age : вывести любой объект из коллекции, значение поля age которого является минимальным");
    }

    @Override
    public void execute() {
        System.out.println(Main.DRAGON_COLLECTION.minByAge());
    }
}