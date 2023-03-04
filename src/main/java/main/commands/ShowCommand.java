package main.commands;

import main.dragons.Dragon;
import main.dragons.DragonCollection;

import java.io.Reader;


public class ShowCommand extends NonArgsCommand {
    private ShowCommand(String name) {
        super(name);
    }

    private static class CommandHolder {
        public static final Command INSTANCE = new ShowCommand("show");
    }

    public static Command getInstance() {
        return CommandHolder.INSTANCE;
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