package main.commands;

import main.dragons.DragonCollection;

import java.io.Reader;

public class ClearCommand extends NonArgsCommand {
    private static ClearCommand clearCommand;

    private ClearCommand(String name) {
        super(name);
    }

    public static ClearCommand getInstance() {
        if (clearCommand == null) clearCommand = new ClearCommand("clear");
        return clearCommand;
    }

    @Override
    public void scriptExecute(Reader reader) {
        execute();
    }

    @Override
    public void execute() {
        DragonCollection.instance.clear();
        System.out.println("Коллекция очищена");
    }
}
