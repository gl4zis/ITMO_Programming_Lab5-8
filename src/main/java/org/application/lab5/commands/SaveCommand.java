package org.application.lab5.commands;

import org.application.lab5.Main;
import org.application.lab5.collection.CollectionManager;
import org.application.lab5.parsers.InputScriptReader;

import java.io.IOException;

public class SaveCommand extends NonArgsCommand {

    SaveCommand() {
        super("save", "save : сохранить коллекцию в файл");
    }

    @Override
    public void execute(InputScriptReader reader) {
        try {
            CollectionManager.saveCollection(Main.JSON_MANAGER);
            System.out.println("Коллекция успешно сохранена в файл");
        } catch (IOException e) {
            System.out.println("Ошибка доступа к файлу");
        }
    }
}