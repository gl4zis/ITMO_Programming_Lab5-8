package main.commands;

import main.dragons.Dragon;
import main.dragons.DragonCollection;

import java.io.Reader;


public class ShowCommand extends NonArgsCommand {
    private static ShowCommand showCommand;

    private ShowCommand(String name) {
        super(name);
    }

    public static ShowCommand getInstance() {
        if (showCommand == null) showCommand = new ShowCommand("show");
        return showCommand;
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