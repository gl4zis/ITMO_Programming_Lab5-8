package org.application.lab5.commands;

public class HelpCommand extends NonArgsCommand {

    HelpCommand() {
        super("help", "help : вывести справку по доступным командам");
    }

    @Override
    public void execute() {
        for (Command command : INVOKER.getCommands()) {
            System.out.println("\t" + command.getDescription());
        }
    }

}
