package org.application.lab5.commands;

import org.application.lab5.parsers.InputScriptReader;

public class HelpCommand extends NonArgsCommand {

    private final CommandManager commandManager;

    HelpCommand(CommandManager commandManager) {
        super("help", "help : вывести справку по доступным командам");
        this.commandManager = commandManager;
    }

    @Override
    public void execute(InputScriptReader reader) {
        for (Command command : commandManager.getCommands()) {
            System.out.println("\t" + command.getDescription());
        }
    }

}
