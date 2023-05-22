package commands;

import client.ClientConnection;
import exceptions.ExitException;
import exceptions.IncorrectInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import parsers.MyScanner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.Security;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class CommandProcessorTest {

    private CommandProcessor processor;
    private final MyScanner console = new MyScanner(System.in);

    @BeforeEach
    void init() {
        ClientConnection conn = Mockito.mock(ClientConnection.class);
        Mockito.when(conn.sendReqGetResp(Mockito.anyString(), Mockito.any())).thenReturn("Some response");
        processor = new CommandProcessor(conn);
    }

    @Test
    void execute() {
        String response = processor.execute("add", console);
        assertEquals("Some response", response);
        assertEquals("Incorrect data inputted: Unknown command", processor.execute("sign_in", console));
        assertEquals("Incorrect data inputted: Unknown command", processor.execute("sign_up", console));
        assertEquals("Some response", processor.execute("kasm", console));
        assertTrue(processor.execute("ping", console).startsWith("Some response"));
        InputStream is = new ByteArrayInputStream("exit\n".getBytes());
        System.setIn(is);
        assertThrows(ExitException.class, () -> processor.execute());
    }
}