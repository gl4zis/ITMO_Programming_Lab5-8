package commands;

import collection.DragonCollection;
import dragons.*;
import network.Request;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoveByIdCommandTest {

    @Test
    void execute() {
        DragonCollection collection = new DragonCollection();
        Dragon dragon = new Dragon(1, "name", new Coordinates(6, 5), new Date(), 100, Color.RED, DragonCharacter.WISE, new DragonHead(0));
        String empty = new RemoveByIdCommand(collection).execute(new Request(CommandType.REMOVE_BY_ID, 1, null));
        assertEquals("No such elements in collection", empty);
        collection.add(dragon);
        String oneDr = new RemoveByIdCommand(collection).execute(new Request(CommandType.REMOVE_BY_ID, 1, null));
        assertEquals("Dragon was successfully removed", oneDr);
        String wrong = new RemoveByIdCommand(collection).execute(new Request(CommandType.REMOVE_BY_ID, "1", null));
        assertEquals("Incorrect command argument", wrong);
    }
}