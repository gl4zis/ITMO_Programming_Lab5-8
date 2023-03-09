package main.commands;

import main.dragons.DragonCollection;

import java.io.Reader;

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
