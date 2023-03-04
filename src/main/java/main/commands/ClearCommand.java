package main.commands;

import main.dragons.DragonCollection;

import java.io.Reader;

public class ClearCommand extends NonArgsCommand {

    ClearCommand() {
        super("clear");
    }

    @Override
    public void execute() {
        DragonCollection.instance.clear();
        System.out.println("Коллекция очищена");
    }
}
