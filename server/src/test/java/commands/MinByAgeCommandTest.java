package commands;

import collection.DragonCollection;
import dragons.*;
import network.Request;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinByAgeCommandTest {

    @Test
    void execute() {
        DragonCollection collection = new DragonCollection();
        Dragon dragon = new Dragon(1, "name", new Coordinates(6, 5), new Date(), 100, Color.RED, DragonCharacter.WISE, new DragonHead(0));
        String empty = new MinByAgeCommand(collection).execute(new Request(CommandType.MIN_BY_AGE, null, null));
        assertEquals("Collection is empty", empty);
        collection.add(dragon);
        String oneDr = new MinByAgeCommand(collection).execute(new Request(CommandType.MIN_BY_AGE, null, null));
        assertEquals(dragon.toString(), oneDr);
    }
}