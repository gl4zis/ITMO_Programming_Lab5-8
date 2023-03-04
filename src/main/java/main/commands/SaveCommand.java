package main.commands;

import main.CollectionWorker;

import java.io.IOException;
import java.io.Reader;

public class SaveCommand extends NonArgsCommand {

    SaveCommand() {
        super("save");
    }

    @Override
    public void execute() {
        try {
            CollectionWorker.saveCollection();
            System.out.println("Коллекция успешно сохранена в файл");
        } catch (IOException e) {
            System.out.println("Ошибка доступа к файлу");
        }
    }
}