package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputScriptReader;

public class ClearCommand extends NonArgsCommand {

    private final DragonCollection collection;

    ClearCommand(DragonCollection collection) {
        super("clear", "clear : очистить коллекцию");
        this.collection = collection;
    }

    @Override
    public void execute(InputScriptReader reader) {
        collection.clear();
        System.out.println("Коллекция очищена");
    }
}
