package org.application.lab5.commands;


import org.application.lab5.parsers.InputScriptReader;

public class ExitCommand extends NonArgsCommand {

    ExitCommand() {
        super("exit", "exit : завершить программу (без сохранения в файл)");
    }

    @Override
    public void execute(InputScriptReader reader) {
        System.exit(0);
    }
}