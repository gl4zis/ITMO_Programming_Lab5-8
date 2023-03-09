package main.commands;

import main.CollectionManager;
import main.JsonManager;
import main.exceptions.IncorrectInputException;

public abstract class Command {

    protected static final JsonManager MANAGER = new JsonManager("config");
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

    public static void generateCollection() {
        CollectionManager.transferCollection(MANAGER.readJSON());
    }

    public abstract void execute() throws IncorrectInputException;

    public abstract void execute(String arg) throws IncorrectInputException;
}
