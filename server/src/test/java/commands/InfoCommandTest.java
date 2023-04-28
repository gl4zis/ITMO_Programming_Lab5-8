package commands;

import collection.DragonCollection;
import network.Request;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InfoCommandTest {

    @Test
    void execute() {
        DragonCollection collection = new DragonCollection();
        String info = new InfoCommand(collection).execute(new Request(CommandType.INFO, null, null));
        assertEquals(collection.toString(), info);
    }
}