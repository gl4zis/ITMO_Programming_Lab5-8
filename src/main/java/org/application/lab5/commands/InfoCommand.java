package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputScriptReader;

public class InfoCommand extends NonArgsCommand {

    private final DragonCollection collection;

    InfoCommand(DragonCollection collection) {
        super("info", "info : вывести в стандартный поток вывода информацию о коллекции");
        this.collection = collection;
    }

    @Override
    public void execute(InputScriptReader reader) {
        System.out.println(collection);
    }
}