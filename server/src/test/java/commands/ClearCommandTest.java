package commands;

import collection.DragonCollection;
import network.Request;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClearCommandTest {

    @Test
    void execute() {
        DragonCollection collection = new DragonCollection();
        new ClearCommand(collection).execute(new Request(CommandType.CLEAR, null, null));
        assertEquals(0, collection.getItems().size());
    }
}