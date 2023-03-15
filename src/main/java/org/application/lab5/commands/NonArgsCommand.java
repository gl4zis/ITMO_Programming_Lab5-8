package org.application.lab5.commands;

import org.application.lab5.exceptions.IncorrectInputException;
import org.application.lab5.parsers.InputScriptReader;

/**
 * Class for commands, without arguments in its line.
 * Example: "add". Have no arguments, despite need to type dragon in next 8 lines
 */

public abstract class NonArgsCommand extends Command {

    /**
     * Standard constructor
     */
    protected NonArgsCommand(String name, String descr) {
        super(name, descr);
    }

    /**
     * Execute method for all commands, extending this class
     *
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public abstract void execute(InputScriptReader reader);

    /**
     * Wrong execute method.
     * Needs to not execute something like that: "add 33"
     * There are arguments in line with non-arg command
     *
     * @throws IncorrectInputException always
     */
    @Override
    public void execute(InputScriptReader reader, String arg) throws IncorrectInputException {
        throw new IncorrectInputException("Неизвестная команда. Введите команду help, чтобы посмотреть информацию о коммандах");
    }
}
