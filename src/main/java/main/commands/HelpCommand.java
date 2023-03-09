package main.commands;

import java.io.Reader;

public class HelpCommand extends NonArgsCommand {

    HelpCommand() {
        super("help", "help : вывести справку по доступным командам");
    }

    @Override
    public void execute() {
        for (Command command : CommandManager.getCommands()) {
            System.out.println("\t" + command.getDescription());
        }
    }

}
