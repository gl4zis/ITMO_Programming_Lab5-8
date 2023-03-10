package org.application.lab5.commands;

import org.application.lab5.exceptions.IncorrectInputException;
import org.application.lab5.parsers.InputScriptReader;

public abstract class NonArgsCommand extends Command {
    protected NonArgsCommand(String name, String descr) {
        super(name, descr);
    }

    @Override
    public abstract void execute(InputScriptReader reader);

    @Override
    public void execute(InputScriptReader reader, String arg) throws IncorrectInputException {
        throw new IncorrectInputException("Неизвестная команда. Введите команду help, чтобы посмотреть информацию о коммандах");
    }
}
