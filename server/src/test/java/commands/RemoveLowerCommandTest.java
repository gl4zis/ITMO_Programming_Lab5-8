package commands;

import collection.DragonCollection;
import dragons.*;
import network.Request;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoveLowerCommandTest {

    @Test
    void execute() {
        DragonCollection collection = new DragonCollection();
        Dragon dragon = new Dragon(1, "aaa", new Coordinates(6, 5), new Date(), 100, Color.RED, DragonCharacter.WISE, new DragonHead(0));
        String empty = new RemoveLowerCommand(collection).execute(new Request(CommandType.REMOVE_LOWER, null, dragon));
        assertEquals("No such elements in collection", empty);
        collection.add(dragon);
        String oneDr = new RemoveLowerCommand(collection).execute(new Request(CommandType.REMOVE_LOWER, null, dragon));
        assertEquals("No such elements in collection", oneDr);
        Dragon dragon1 = new Dragon("name", new Coordinates(6, 5), 100, Color.RED, DragonCharacter.WISE, new DragonHead(0));
        oneDr = new RemoveLowerCommand(collection).execute(new Request(CommandType.REMOVE_LOWER, null, dragon1));
        assertEquals("Removed dragon aaa with id: 1", oneDr);
    }
}