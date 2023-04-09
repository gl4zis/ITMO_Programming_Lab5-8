package commands;

import collection.DragonCollection;
import dragons.Dragon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.InputConsoleReader;
import parsers.InputScriptReader;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Non-argument command "sort_by_name". Sorts collection elements by name and id
 */
public class SortCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(SortCommand.class);
    private final DragonCollection collection;
    private final Map<Integer, Comparator<Dragon>> comparators = new HashMap<>();

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    SortCommand(DragonCollection collection) {
        super("sort", "sort {parameter} : отсортировать коллекцию по данному параметру");
        this.collection = collection;
        addDragonComparators();
    }

    /**
     * Adds all dragon comparators to HashMap
     */
    private void addDragonComparators() {
        comparators.put(1, Dragon.idComp);
        comparators.put(2, Dragon.nameComp);
        comparators.put(3, Dragon.coordComp);
        comparators.put(4, Dragon.dateComp);
        comparators.put(5, Dragon.weightComp);
        comparators.put(6, Dragon.ageComp);
        comparators.put(7, Dragon.colorComp);
        comparators.put(8, Dragon.charComp);
        comparators.put(9, Dragon.headComp);
    }

    /**
     * Sorts collection elements by name and id or outputs message about empty collection
     *
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public String execute(InputScriptReader reader) {
        if (collection.getItems().size() > 0) {
            String message = """
                    1: Id
                    2: Name
                    3: Coordinates
                    4: Date of creation
                    5: Weight
                    6: Age
                    7: Color
                    8: Character
                    9: Head
                    Choose parameter, by that collection will be sorted (input number):\040""";
            int number = InputConsoleReader.readLongVar(false, message, 1, 9).intValue();
            collection.sort(comparators.get(number));
            LOGGER.info("Collection was sorted");
        } else System.out.println("Collection is empty");
        LOGGER.debug("Sort command was successfully executed");
        return "";
    }
}
