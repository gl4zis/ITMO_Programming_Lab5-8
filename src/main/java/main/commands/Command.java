package main.commands;

import main.CollectionManager;
import main.JsonManager;
import main.exceptions.IncorrectInputException;

public abstract class Command {

    protected static final JsonManager MANAGER = new JsonManager("config");
    private final String NAME;

    protected Command(String name) {
        this.NAME = name;
    }

    public String getName() {
        return NAME;
    }

    public static void generateCollection() {
        CollectionManager.transferCollection(MANAGER.readJSON());
    }

    public abstract void execute() throws IncorrectInputException;

    public abstract void execute(String arg) throws IncorrectInputException;
}
