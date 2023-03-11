package org.application.lab5.general;

import org.application.lab5.collection.CollectionManager;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.commands.CommandManager;
import org.application.lab5.exceptions.IncorrectInputException;
import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.parsers.JsonManager;

public class Main {
    public static void main(String[] args) {
        JsonManager jsonManager = new JsonManager("ccc");
        DragonCollection collection = new DragonCollection();
        CommandManager commandManager = new CommandManager(jsonManager, collection);
        CollectionManager.transferCollection(jsonManager.readJSON(), collection);
        while (true) {
            try {
                commandManager.seekCommand(null, InputConsoleReader.readNextLine());
            } catch (IncorrectInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}