package main.commands;

import main.exceptions.IncorrectInputException;

public abstract class Command {

    private final String NAME;

    protected Command(String name) {
        this.NAME = name;
    }

    public String getName() {
        return NAME;
    }

    public abstract void execute() throws IncorrectInputException;

    public abstract void execute(String arg) throws IncorrectInputException;
}
