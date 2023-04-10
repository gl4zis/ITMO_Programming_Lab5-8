package commands;

import collection.DragonCollection;
import dragons.Dragon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * Argument command "filter_less_than_weight weight".
 * Output all dragons in collection, which weight less than inputted weight
 */
public class FilterLessThanWeightCommand extends ArgsCommand {
    private final Logger LOGGER = LogManager.getLogger(FilterLessThanWeightCommand.class);
    private final DragonCollection collection;

    /**
     * Constructor, sets collection, that the command works with, name and description of command
     */
    FilterLessThanWeightCommand(DragonCollection collection) {
        super("filter_less_than_weight",
                "filter_less_than_weight weight : вывести элементы, значение поля weight которых меньше заданного");
        this.collection = collection;
    }

    /**
     * Output all dragons in collection, which weight less than inputted weight
     * If inputted arg is not number, outputs error message
     *
     * @param arg weight, to compare dragon weights with it
     */
    @Override
    public String execute(String arg) {
        try {
            ArrayList<Dragon> dragons = new ArrayList<>();
            long weight = Long.parseLong(arg);
            int counter = 0;
            for (Dragon dragon : collection.getItems()) {
                if (dragon.getWeight() < weight) {
                    dragons.add(dragon);
                    counter++;
                }
            }
            if (counter == 0) {
                LOGGER.debug("FilterLessThanWeight command was successfully executed");
                return "Collection is empty";
            } else {
                StringBuilder line = new StringBuilder();
                for (Dragon dragon : dragons) {
                    line.append(dragon.toString());
                }
                LOGGER.debug("FilterLessThanWeight command was successfully executed");
                return line.toString();
            }
        } catch (NumberFormatException e) {
            LOGGER.warn("Incorrect command argument");
            LOGGER.debug("FilterLessThanWeight command was successfully executed");
            return "Incorrect command argument";
        }
    }
}