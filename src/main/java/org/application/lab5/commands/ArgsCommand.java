package org.application.lab5.commands;

import org.application.lab5.exceptions.IncorrectInputException;
import org.application.lab5.parsers.InputScriptReader;

public abstract class ArgsCommand extends Command {

    protected ArgsCommand(String name, String descr) {
        super(name, descr);
    }

    @Override
    public abstract void execute(InputScriptReader reader, String arg);

    @Override
    public void execute(InputScriptReader reader) throws IncorrectInputException {
        throw new IncorrectInputException("Неизвестная команда. Введите команду help, чтобы посмотреть информацию о коммандах");
    }

}
