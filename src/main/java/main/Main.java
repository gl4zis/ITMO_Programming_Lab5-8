package main;

import main.commands.CommandManager;
import main.exceptions.IncorrectInputException;

public class Main {

    public static void main(String[] args) {
        CollectionManager.transferCollection(JsonManager.readJSON());
        CommandManager manager = new CommandManager();
        while (true) {
            try {
                manager.seekCommand(InputConsoleReader.readNextLine());
            } catch (IncorrectInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}