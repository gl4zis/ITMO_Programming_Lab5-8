package org.application.lab5.commands;

import org.application.lab5.Main;
import org.application.lab5.collection.DragonCollection;

public class InfoCommand extends NonArgsCommand {

    InfoCommand() {
        super("info", "info : вывести в стандартный поток вывода информацию о коллекции");
    }

    @Override
    public void execute() {
        System.out.println(Main.DRAGON_COLLECTION);
    }
}