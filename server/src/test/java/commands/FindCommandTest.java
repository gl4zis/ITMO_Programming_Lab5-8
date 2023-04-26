package commands;

import collection.DragonCollection;
import dragons.*;
import network.Request;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FindCommandTest {

    @Test
    void execute() {
        DragonCollection collection = new DragonCollection();
        Dragon dragon = new Dragon(1, "name", new Coordinates(6, 5), new Date(), 100, Color.RED, DragonCharacter.WISE, new DragonHead(0));
        String empty = new FindCommand(collection).execute(new Request(CommandType.FIND, 1, null));
        assertEquals("No such element in collection", empty);
        collection.add(dragon);
        String oneDr = new FindCommand(collection).execute(new Request(CommandType.FIND, 1, null));
        assertEquals(dragon.toString(), oneDr);
    }
}