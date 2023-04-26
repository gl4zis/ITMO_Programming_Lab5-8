package commands;

import collection.DragonCollection;
import network.Request;
import org.junit.jupiter.api.Test;
import parsers.JsonManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class HelpCommandTest {

    @Test
    void execute() {
        InputStream is = new ByteArrayInputStream("~/Desktop/ITMO/Programming/collection.json\n".getBytes());
        System.setIn(is);
        CommandManager manager = new CommandManager(new JsonManager(""), new DragonCollection());
        String help = new HelpCommand(manager).execute(new Request(CommandType.HELP, null, null));
        assertNotNull(help);
        assertTrue(help.length() > 0);
    }
}