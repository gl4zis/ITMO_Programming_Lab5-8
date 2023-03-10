package org.application.lab5.commands;

import org.application.lab5.Main;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputScriptReader;

public class ClearCommand extends NonArgsCommand {

    ClearCommand() {
        super("clear", "clear : очистить коллекцию");
    }

    @Override
    public void execute(InputScriptReader reader) {
        Main.DRAGON_COLLECTION.clear();
        System.out.println("Коллекция очищена");
    }
}
