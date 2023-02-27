package main.commands;

import main.dragons.DragonCollection;

import java.io.Reader;

public class InfoCommand extends NonArgsCommand {
    private static InfoCommand infoCommand;

    private InfoCommand(String name) {
        super(name);
    }

    public static InfoCommand getInstance() {
        if (infoCommand == null) infoCommand = new InfoCommand("info");
        return infoCommand;
    }

    @Override
    public void scriptExecute(Reader reader) {
        execute();
    }

    @Override
    public void execute() {
        System.out.println(DragonCollection.instance);
    }
}