package main.commands;

import main.exceptions.IncorrectInputException;

import java.io.Reader;

public abstract class ArgsCommand extends Command {

    protected ArgsCommand(String name, String descr) {
        super(name, descr);
    }

    public abstract void execute(String arg);

    public void execute() throws IncorrectInputException {
        throw new IncorrectInputException("Неизвестная команда. Введите команду help, чтобы посмотреть информацию о коммандах");
    }

}
