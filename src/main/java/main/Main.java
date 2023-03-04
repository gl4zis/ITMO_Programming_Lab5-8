package main;

import main.commands.Command;
import main.commands.CommandManager;
import main.exceptions.IncorrectInputException;

public class Main {

    public static void main(String[] args) {
        CollectionWorker.transferCollection(JsonWorker.readJSON());
        CommandManager manager = new CommandManager();
        while (true) {
            try {
                manager.seekCommand(InputReader.readNextConsoleLine());
            } catch (IncorrectInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}