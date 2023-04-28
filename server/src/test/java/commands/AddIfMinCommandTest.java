package commands;

import collection.DragonCollection;
import dragons.*;
import network.Request;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddIfMinCommandTest {

    DragonCollection collection = new DragonCollection();


    @Test
    void execute() {
        Dragon dragon = new Dragon(1, "name", new Coordinates(6, 5), new Date(), 3, Color.RED, DragonCharacter.WISE, new DragonHead(0));
        collection.add(dragon);
        new AddIfMinCommand(collection).execute(new Request(CommandType.ADD_IF_MIN, null, dragon));
        assertEquals(1, collection.getItems().size());
        dragon = new Dragon(2, "aaa", new Coordinates(6, 5), new Date(), 3, Color.RED, DragonCharacter.WISE, new DragonHead(0));
        new AddIfMinCommand(collection).execute(new Request(CommandType.ADD_IF_MIN, null, dragon));
        assertEquals(2, collection.getItems().size());
    }
}