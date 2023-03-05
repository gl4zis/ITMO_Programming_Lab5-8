package main;

import main.commands.CommandManager;
import main.exceptions.IncorrectInputException;
import org.json.simple.JSONObject;

public class Main {
    public static JsonManager jsonManager = new JsonManager("config");

    public static void main(String[] args) {
        CollectionManager.transferCollection(jsonManager.readJSON());
        while (true) {
            try {
                CommandManager.seekCommand(InputConsoleReader.readNextLine());
            } catch (IncorrectInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}