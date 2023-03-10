package org.application.lab5.commands;

import org.application.lab5.Main;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputScriptReader;

public class InfoCommand extends NonArgsCommand {

    InfoCommand() {
        super("info", "info : вывести в стандартный поток вывода информацию о коллекции");
    }

    @Override
    public void execute(InputScriptReader reader) {
        System.out.println(Main.DRAGON_COLLECTION);
    }
}