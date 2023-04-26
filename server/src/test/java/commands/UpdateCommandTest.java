package commands;

import collection.DragonCollection;
import dragons.*;
import network.Request;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UpdateCommandTest {

    @Test
    void execute() {
        DragonCollection collection = new DragonCollection();
        Dragon dragon = new Dragon(1, "aaa", new Coordinates(6, 5), new Date(), 100, Color.RED, DragonCharacter.WISE, new DragonHead(0));
        Dragon dragon1 = new Dragon("name", new Coordinates(6, 5), 100, Color.RED, DragonCharacter.WISE, new DragonHead(0));

        String empty = new UpdateCommand(collection).execute(new Request(CommandType.UPDATE, 1, dragon1));
        assertEquals("No such element in collection", empty);
        collection.add(dragon);
        String oneDr = new UpdateCommand(collection).execute(new Request(CommandType.UPDATE, 1, dragon1));
        assertEquals("Dragon was updated", oneDr);
        oneDr = new UpdateCommand(collection).execute(new Request(CommandType.UPDATE, null, dragon1));
        assertEquals("Incorrect command argument", oneDr);
    }
}