package main.commands;

import main.dragons.DragonCollection;

import java.io.Reader;

public class InfoCommand extends NonArgsCommand {

    InfoCommand() {
        super("info");
    }

    @Override
    public void execute() {
        System.out.println(DragonCollection.instance);
    }
}