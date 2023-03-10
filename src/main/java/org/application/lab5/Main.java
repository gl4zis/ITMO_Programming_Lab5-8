package org.application.lab5;

import org.application.lab5.collection.CollectionManager;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.commands.CommandManager;
import org.application.lab5.exceptions.IncorrectInputException;
import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.parsers.JsonManager;

public class Main {
    public static final CommandManager COMMAND_MANAGER = new CommandManager();
    public static final JsonManager JSON_MANAGER = new JsonManager("config");
    public static final DragonCollection DRAGON_COLLECTION = new DragonCollection();

    public static void main(String[] args) {
        CollectionManager.transferCollection(JSON_MANAGER.readJSON());
        while (true) {
            System.out.print("-> ");
            try {
                Main.COMMAND_MANAGER.seekCommand(InputConsoleReader.readNextLine());
            } catch (IncorrectInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}