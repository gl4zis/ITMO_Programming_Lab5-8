package main.commands;

import java.io.Reader;

public class ExitCommand extends NonArgsCommand {

    ExitCommand() {
        super("exit");
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}