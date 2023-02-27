package main.commands;

import main.exceptions.IncorrectInputException;

import java.io.Reader;

public abstract class NonArgsCommand extends Command {
    protected NonArgsCommand(String name) {
        super(name);
        haveArgs = false;
    }

    public abstract void execute();

    public void execute(String arg) throws IncorrectInputException {
        throw new IncorrectInputException("Неизвестная команда. Введите команду help, чтобы посмотреть информацию о коммандах");
    }

    public void scriptExecute(Reader reader, String arg) throws IncorrectInputException {
        throw new IncorrectInputException("Неизвестная комнда");
    }
}
