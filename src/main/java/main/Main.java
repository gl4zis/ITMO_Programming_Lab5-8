package main;

import main.commands.Command;
import main.commands.CommandManager;
import main.exceptions.IncorrectInputException;

public class Main {

    public static void main(String[] args) {
        Command.generateCollection();
        while (true) {
            try {
                System.out.print("-> ");
                CommandManager.seekCommand(InputConsoleReader.readNextLine());
            } catch (IncorrectInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}