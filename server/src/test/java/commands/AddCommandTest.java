package commands;

import collection.DragonCollection;
import dragons.*;
import network.Request;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AddCommandTest {

    DragonCollection collection = new DragonCollection();

    @Test
    void execute() {
        Dragon dragon = new Dragon(1, "name", new Coordinates(6, 5), new Date(), 3, Color.RED, DragonCharacter.WISE, new DragonHead(0));
        collection.add(dragon);
        new AddCommand(collection).execute(new Request(CommandType.ADD, null, dragon));
        assertEquals(2, collection.getItems().size());
    }
}