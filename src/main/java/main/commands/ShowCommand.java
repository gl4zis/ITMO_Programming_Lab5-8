package main.commands;

import main.dragons.Dragon;
import main.dragons.DragonCollection;

import java.io.Reader;


public class ShowCommand extends NonArgsCommand {

    ShowCommand() {
        super("show");
    }

    @Override
    public void execute() {
        if (DragonCollection.instance.getItems().size() == 0) {
            System.out.println("Нет элементов в коллекции");
        } else {
            int counter = 0;
            for (Dragon dragon : DragonCollection.instance.getItems()) {
                if (counter >= 1)
                    System.out.println("----------------------------------------------------------------");
                System.out.println(dragon);
                counter++;
            }
        }
    }
}