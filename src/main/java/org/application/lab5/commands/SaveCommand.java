package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.collection.CollectionManager;
import org.application.lab5.parsers.InputScriptReader;
import org.application.lab5.parsers.JsonManager;

import java.io.IOException;

public class SaveCommand extends NonArgsCommand {

    private final JsonManager jsonManager;
    private final DragonCollection collection;

    SaveCommand(JsonManager jsonManager, DragonCollection collection) {
        super("save", "save : сохранить коллекцию в файл");
        this.collection = collection;
        this.jsonManager = jsonManager;
    }

    @Override
    public void execute(InputScriptReader reader) {
        try {
            CollectionManager.saveCollection(jsonManager, collection);
            System.out.println("Коллекция успешно сохранена в файл");
        } catch (IOException e) {
            System.out.println("Ошибка доступа к файлу");
        }
    }
}