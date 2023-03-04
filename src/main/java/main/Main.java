package main;

import main.commands.Command;
import main.exceptions.IncorrectInputException;

public class Main {
    public static void main(String[] args) {
        CollectionWorker.transferCollection(JsonWorker.readJSON());
        while (true) {
            try {
                Command.seekCommand(InputReader.readNextConsoleLine());
            } catch (IncorrectInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}