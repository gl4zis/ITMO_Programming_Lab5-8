package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.parsers.InputScriptReader;

/**
 * Non-argument command "min_by_age". Outputs one dragon from collection, which have minimum age
 */

public class MinByAgeCommand extends NonArgsCommand {

    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that command works with, name and description of this command
     */
    MinByAgeCommand(DragonCollection collection) {
        super("min_by_age",
                "min_by_age : вывести любой объект из коллекции, значение поля age которого является минимальным");
        this.collection = collection;
    }

    /**
     * Outputs one dragon from collection, which have minimum age or outputs message about empty collection
     *
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public void execute(InputScriptReader reader) {
        Dragon minDragon = collection.minByAge();
        if (minDragon == null) System.out.println("Нет объектов в коллекции");
        else System.out.println(collection.minByAge());
    }
}