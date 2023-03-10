package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;

public class ClearCommand extends NonArgsCommand {

    ClearCommand() {
        super("clear", "clear : очистить коллекцию");
    }

    @Override
    public void execute() {
        DragonCollection.instance.clear();
        System.out.println("Коллекция очищена");
    }
}
