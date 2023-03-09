package main;

import main.commands.Command;
import main.commands.CommandManager;
import main.exceptions.IncorrectInputException;

public class Main {

    public static void main(String[] args) {
        Command.generateCollection();
        while (true) {
            System.out.print("-> ");
            Command.parse();
        }
    }
}