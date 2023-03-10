package org.application.lab5.commands;

import org.application.lab5.exceptions.IncorrectInputException;

public abstract class ArgsCommand extends Command {

    protected ArgsCommand(String name, String descr) {
        super(name, descr);
    }

    public abstract void execute(String arg);

    public void execute() throws IncorrectInputException {
        throw new IncorrectInputException("Неизвестная команда. Введите команду help, чтобы посмотреть информацию о коммандах");
    }

}
