package main.commands;

import main.InputReader;
import main.dragons.*;
import main.exceptions.IncorrectInputException;

import java.util.HashMap;
import java.util.Map;

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
