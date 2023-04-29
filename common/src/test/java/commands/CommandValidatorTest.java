package commands;

import org.junit.jupiter.api.Test;

class CommandValidatorTest {

    @Test
    void validCommandFromLine() {
        /*InputStream is = new ByteArrayInputStream("name\n6\n5\n4\n3\n2\n1\n0\n".getBytes());
        MyScanner scanner = new MyScanner(is);
        assertThrows(IncorrectInputException.class, () -> CommandValidator.validCommand("command with a lot of args", scanner));
        assertThrows(IncorrectInputException.class, () -> CommandValidator.validCommand("jkn", scanner));
        assertEquals(new Request(CommandType.HELP, null, null), CommandValidator.validCommand("help", scanner));
        assertThrows(IncorrectInputException.class, () -> CommandValidator.validCommand("update", scanner));
        assertThrows(IncorrectInputException.class, () -> CommandValidator.validCommand("update lol", scanner));

        Request removeReq = CommandValidator.validCommand("remove_by_id 123", scanner);
        assertEquals(CommandType.REMOVE_BY_ID, removeReq.command());
        assertEquals(123, removeReq.arg());
        assertNull(removeReq.dragon());

        Request filterReq = CommandValidator.validCommand("filter_less_than_weight 123", scanner);
        assertEquals(CommandType.FILTER_LESS_THAN_WEIGHT, filterReq.command());
        assertEquals((long) 123, filterReq.arg());
        assertNull(filterReq.dragon());

        Request addReq = CommandValidator.validCommand("add", scanner);
        assertEquals(CommandType.ADD, addReq.command());
        assertNull(addReq.arg());
        assertNotNull(addReq.dragon());

         */
    }

    @Test
    void ValidCommandFromRequest() {
        /*Request nullReq = new Request(null, null, null);
        assertFalse(CommandValidator.validCommand(nullReq));
        Request helpReq = new Request(CommandType.HELP, null, null);
        assertTrue(CommandValidator.validCommand(helpReq));
        Request removeReq = new Request(CommandType.REMOVE_BY_ID, 123, null);
        assertTrue(CommandValidator.validCommand(removeReq));
        Dragon dragon = new Dragon("name", new Coordinates(6, 5), 3, Color.RED, DragonCharacter.WISE, new DragonHead(0));
        Request addReq = new Request(CommandType.ADD, null, dragon);
        assertTrue(CommandValidator.validCommand(addReq));
        Request filterReq = new Request(CommandType.FILTER_LESS_THAN_WEIGHT, (long) 123, null);
        assertTrue(CommandValidator.validCommand(filterReq));
        Request wrongReq = new Request(CommandType.REMOVE_BY_ID, null, null);
        assertFalse(CommandValidator.validCommand(wrongReq));
        wrongReq = new Request(CommandType.ADD, null, null);
        assertFalse(CommandValidator.validCommand(wrongReq));
        wrongReq = new Request(CommandType.FILTER_LESS_THAN_WEIGHT, 123, null);
        assertFalse(CommandValidator.validCommand(wrongReq));

         */
    }
}