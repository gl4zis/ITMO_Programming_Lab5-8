package commands;

import dragons.*;
import exceptions.IncorrectInputException;
import network.Request;
import org.junit.jupiter.api.Test;
import parsers.MyScanner;
import user.User;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class CommandValidatorTest {

    @Test
    void validCommandFromLine() {
        User user = User.signUp("admin", "qwerty");
        InputStream is = new ByteArrayInputStream("name\n6\n5\n4\n3\n2\n1\n0\n".getBytes());
        MyScanner scanner = new MyScanner(is);
        assertThrows(IncorrectInputException.class, () -> CommandValidator.validCommand("command with a lot of args", scanner, user));
        assertThrows(IncorrectInputException.class, () -> CommandValidator.validCommand("jkn", scanner, user));
        assertEquals(new Request(CommandType.HELP, null, null, user), CommandValidator.validCommand("help", scanner, user));
        assertThrows(IncorrectInputException.class, () -> CommandValidator.validCommand("update", scanner, user));
        assertThrows(IncorrectInputException.class, () -> CommandValidator.validCommand("update lol", scanner, user));

        Request removeReq = CommandValidator.validCommand("remove_by_id 123", scanner, user);
        assertEquals(CommandType.REMOVE_BY_ID, removeReq.command());
        assertEquals(123, removeReq.arg());
        assertNull(removeReq.dragon());

        Request filterReq = CommandValidator.validCommand("filter_less_than_weight 123", scanner, user);
        assertEquals(CommandType.FILTER_LESS_THAN_WEIGHT, filterReq.command());
        assertEquals((long) 123, filterReq.arg());
        assertNull(filterReq.dragon());

        Request addReq = CommandValidator.validCommand("add", scanner, user);
        assertEquals(CommandType.ADD, addReq.command());
        assertNull(addReq.arg());
        assertNotNull(addReq.dragon());
    }

    @Test
    void ValidCommandFromRequest() {
        User user = User.signUp("admin", "qwerty");
        Request nullReq = new Request(null, null, null, user);
        assertFalse(CommandValidator.validCommand(nullReq));
        Request helpReq = new Request(CommandType.HELP, null, null, user);
        assertTrue(CommandValidator.validCommand(helpReq));
        Request removeReq = new Request(CommandType.REMOVE_BY_ID, 123, null, user);
        assertTrue(CommandValidator.validCommand(removeReq));
        Dragon dragon = new Dragon("name", new Coordinates(6, 5), 3, Color.RED, DragonCharacter.WISE, new DragonHead(0), user);
        Request addReq = new Request(CommandType.ADD, null, dragon, user);
        assertTrue(CommandValidator.validCommand(addReq));
        Request filterReq = new Request(CommandType.FILTER_LESS_THAN_WEIGHT, 123L, null, user);
        assertTrue(CommandValidator.validCommand(filterReq));
        Request wrongReq = new Request(CommandType.REMOVE_BY_ID, null, null, user);
        assertFalse(CommandValidator.validCommand(wrongReq));
        wrongReq = new Request(CommandType.ADD, null, null, user);
        assertFalse(CommandValidator.validCommand(wrongReq));
        wrongReq = new Request(CommandType.FILTER_LESS_THAN_WEIGHT, 123, null, user);
        assertFalse(CommandValidator.validCommand(wrongReq));
    }
}