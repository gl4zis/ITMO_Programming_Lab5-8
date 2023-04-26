package commands;

import collection.DragonCollection;
import dragons.*;
import network.Request;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AverageOfWeightCommandTest {

    @Test
    void execute() {
        DragonCollection collection = new DragonCollection();
        Dragon dragon = new Dragon("name", new Coordinates(6, 5), 3, Color.RED, DragonCharacter.WISE, new DragonHead(0));
        collection.add(dragon);
        String result = new AverageOfWeightCommand(collection).execute(new Request(CommandType.AVERAGE_OF_WEIGHT, null, null));
        assertEquals("Average weight = 3", result);
    }
}