package main.commands;

import java.io.Reader;

public class ExitCommand extends NonArgsCommand {
    private ExitCommand(String name) {
        super(name);
    }

    private static class CommandHolder {
        public static final Command INSTANCE = new ExitCommand("exit");
    }

    public static Command getInstance() {
        return CommandHolder.INSTANCE;
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}