package commands;

import collection.DragonCollection;
import dragons.*;
import network.Request;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShowCommandTest {

    @Test
    void execute() {
        DragonCollection collection = new DragonCollection();
        Dragon dragon = new Dragon(1, "aaa", new Coordinates(6, 5), new Date(), 100, Color.RED, DragonCharacter.WISE, new DragonHead(0));
        String empty = new ShowCommand(collection).execute(new Request(CommandType.SHOW, null, null));
        assertEquals("No such elements in collection", empty);
        collection.add(dragon);
        String oneDr = new ShowCommand(collection).execute(new Request(CommandType.SHOW, null, null));
        assertEquals(dragon.toString(), oneDr);
    }
}