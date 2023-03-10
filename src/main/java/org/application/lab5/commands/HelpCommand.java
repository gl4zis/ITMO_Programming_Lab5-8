package org.application.lab5.commands;

import org.application.lab5.Main;

public class HelpCommand extends NonArgsCommand {

    HelpCommand() {
        super("help", "help : вывести справку по доступным командам");
    }

    @Override
    public void execute() {
        for (Command command : Main.COMMAND_MANAGER.getCommands()) {
            System.out.println("\t" + command.getDescription());
        }
    }

}
