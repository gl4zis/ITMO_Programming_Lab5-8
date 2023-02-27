package main.commands;

import main.CollectionWorker;

import java.io.IOException;
import java.io.Reader;

public class SaveCommand extends NonArgsCommand {
    private static SaveCommand saveCommand;

    private SaveCommand(String name) {
        super(name);
    }


    public static SaveCommand getInstance() {
        if (saveCommand == null) saveCommand = new SaveCommand("save");
        return saveCommand;
    }

    @Override
    public void scriptExecute(Reader reader) {
        execute();
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