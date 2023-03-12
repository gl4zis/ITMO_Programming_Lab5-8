package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputScriptReader;

public class SortByNameCommand extends NonArgsCommand {

    private final DragonCollection collection;

    SortByNameCommand(DragonCollection collection) {
        super("sort_by_name", "sort_by_name : отсортировать коллекцию по имени (если имена одинаковые, то по Id)");
        this.collection = collection;
    }

    @Override
    public void execute(InputScriptReader reader) {
        if (collection.getItems().size() > 0) {
            collection.sort();
            System.out.println("Коллекция отсортирована");
        } else System.out.println("Нет элементов в коллекции");
    }
}
