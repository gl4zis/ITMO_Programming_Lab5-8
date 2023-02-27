package main.commands;

import java.io.Reader;

public class ExitCommand extends NonArgsCommand {
    private static ExitCommand exitCommand;

    private ExitCommand(String name) {
        super(name);
    }

    public static ExitCommand getInstance() {
        if (exitCommand == null) exitCommand = new ExitCommand("exit");
        return exitCommand;
    }

    @Override
    public void scriptExecute(Reader reader) {
        execute();
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}