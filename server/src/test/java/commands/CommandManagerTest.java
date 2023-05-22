package commands;

import collection.DragonCollection;
import database.DataBaseManager;
import network.Request;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandManagerTest {

    private Connection conn;
    private DragonCollection collection;
    private CommandManager manager;

    @Test
    void parse() {
        try {
            Method parse = CommandManager.class.getDeclaredMethod("parseCommandName", String.class);
            parse.setAccessible(true);
            String className = (String) parse.invoke(CommandManager.class, "add_if_min");
            assertEquals("commands.AddIfMinCommand", className);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
        }
    }

    @Test
    void seekCommand() {
        conn = Mockito.mock(Connection.class);
        DataBaseManager baseMan = new DataBaseManager(conn);
        collection = new DragonCollection();
        manager = new CommandManager(baseMan, collection);
        assertEquals(18, manager.getCommands().size());
        assertEquals(collection, manager.getCollection());
        assertEquals(baseMan, manager.getBaseMan());
        Request request = new Request(CommandType.SHOW, null, null, null);
        assertEquals("Collection is empty", manager.seekCommand(request));
    }
}