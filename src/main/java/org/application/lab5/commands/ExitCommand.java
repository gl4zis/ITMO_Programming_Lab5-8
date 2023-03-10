package org.application.lab5.commands;


public class ExitCommand extends NonArgsCommand {

    ExitCommand() {
        super("exit", "exit : завершить программу (без сохранения в файл)");
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}