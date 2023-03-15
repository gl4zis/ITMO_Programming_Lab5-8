package org.application.lab5.general;

import org.application.lab5.collection.CollectionManager;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.commands.CommandManager;
import org.application.lab5.exceptions.IncorrectInputException;
import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.parsers.JsonManager;

/**
 * Main class, that starts the application
 */

public class Main {
    /** Executable method, that starts the application.
     * Creates all necessary objects (JsonManager, DragonCollection, CommandManager),
     * Fills out collection from JSON file and waits for command ind console line
     */
    public static void main(String[] args) {
        JsonManager jsonManager = new JsonManager("config");
        DragonCollection collection = new DragonCollection();
        CommandManager commandManager = new CommandManager(jsonManager, collection);
        CollectionManager.uploadCollection(jsonManager.readJSON(), collection);
        while (true) {
            try {
                System.out.print("-> ");
                commandManager.seekCommand(InputConsoleReader.readNextLine());
            } catch (IncorrectInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}