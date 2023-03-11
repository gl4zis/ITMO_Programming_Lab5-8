package org.application.lab5.commands;

import org.application.lab5.parsers.InputScriptReader;
import org.application.lab5.exceptions.IncorrectInputException;

public abstract class Command {

    private final String name;
    private final String descr;

    protected Command(String name, String descr) {
        this.name = name;
        this.descr = descr;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return descr;
    }

    public abstract void execute(InputScriptReader reader) throws IncorrectInputException;

    public abstract void execute(InputScriptReader reader, String arg) throws IncorrectInputException;
}
