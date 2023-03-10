package org.application.lab5.commands;

import org.application.lab5.Main;
import org.application.lab5.collection.DragonCollection;

public class ClearCommand extends NonArgsCommand {

    ClearCommand() {
        super("clear", "clear : очистить коллекцию");
    }

    @Override
    public void execute() {
        Main.DRAGON_COLLECTION.clear();
        System.out.println("Коллекция очищена");
    }
}
