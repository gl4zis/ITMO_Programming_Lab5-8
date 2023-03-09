package main.commands;

import java.io.Reader;

public class ExitCommand extends NonArgsCommand {

    ExitCommand() {
        super("exit", "exit : завершить программу (без сохранения в файл)");
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}