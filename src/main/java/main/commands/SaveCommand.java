package main.commands;

import main.CollectionManager;

import java.io.IOException;

public class SaveCommand extends NonArgsCommand {

    SaveCommand() {
        super("save", "save : сохранить коллекцию в файл");
    }

    @Override
    public void execute() {
        try {
            CollectionManager.saveCollection(MANAGER);
            System.out.println("Коллекция успешно сохранена в файл");
        } catch (IOException e) {
            System.out.println("Ошибка доступа к файлу");
        }
    }
}