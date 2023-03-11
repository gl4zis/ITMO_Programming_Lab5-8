package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.parsers.InputScriptReader;


public class ShowCommand extends NonArgsCommand {

    private final DragonCollection collection;

    ShowCommand(DragonCollection collection) {
        super("show", "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collection = collection;
    }

    @Override
    public void execute(InputScriptReader reader) {
        if (collection.getItems().size() == 0) {
            System.out.println("Нет элементов в коллекции");
        } else {
            int counter = 0;
            for (Dragon dragon : collection.getItems()) {
                if (counter >= 1)
                    System.out.println("----------------------------------------------------------------");
                System.out.println(dragon);
                counter++;
            }
        }
    }
}