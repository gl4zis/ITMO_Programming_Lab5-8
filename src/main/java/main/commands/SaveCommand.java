package main.commands;

import main.CollectionWorker;

import java.io.IOException;
import java.io.Reader;

public class SaveCommand extends NonArgsCommand {
    private SaveCommand(String name) {
        super(name);
    }

    private static class CommandHolder {
        public static final Command INSTANCE = new SaveCommand("save");
    }

    public static Command getInstance() {
        return CommandHolder.INSTANCE;
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