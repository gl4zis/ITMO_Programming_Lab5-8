package org.application.lab5.commands;

import org.application.lab5.exceptions.IncorrectInputException;

public abstract class NonArgsCommand extends Command {
    protected NonArgsCommand(String name, String descr) {
        super(name, descr);
    }

    public abstract void execute();

    public void execute(String arg) throws IncorrectInputException {
        throw new IncorrectInputException("Неизвестная команда. Введите команду help, чтобы посмотреть информацию о коммандах");
    }
}
