package commands;

import collection.DragonCollection;
import dragons.*;
import network.Request;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilterLessThanWeightCommandTest {

    @Test
    void execute() {
        DragonCollection collection = new DragonCollection();
        Dragon dragon = new Dragon("name", new Coordinates(6, 5), 100, Color.RED, DragonCharacter.WISE, new DragonHead(0));
        String empty = new FilterLessThanWeightCommand(collection).execute(new Request(CommandType.FILTER_LESS_THAN_WEIGHT, (long) 123, null));
        assertEquals("No such elements in collection", empty);
        collection.add(dragon);
        String oneDr = new FilterLessThanWeightCommand(collection).execute(new Request(CommandType.FILTER_LESS_THAN_WEIGHT, (long) 123, null));
        assertEquals(dragon.toString(), oneDr);
        String incorrect = new FilterLessThanWeightCommand(collection).execute(new Request(CommandType.FILTER_LESS_THAN_WEIGHT, "123", null));
        assertEquals("Incorrect command argument", incorrect);
    }
}