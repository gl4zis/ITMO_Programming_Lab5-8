package org.application.lab5.commands;

import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.parsers.JsonManager;
import org.application.lab5.collection.CollectionManager;
import org.application.lab5.exceptions.IncorrectInputException;

public abstract class Command {

    protected static final JsonManager MANAGER = new JsonManager("config");
    protected static final CommandManager INVOKER = new CommandManager();
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

    public static void parse() {
        try {
            INVOKER.seekCommand(InputConsoleReader.readNextLine());
        } catch (IncorrectInputException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void generateCollection() {
        CollectionManager.transferCollection(MANAGER.readJSON());
    }

    public abstract void execute() throws IncorrectInputException;

    public abstract void execute(String arg) throws IncorrectInputException;
}
