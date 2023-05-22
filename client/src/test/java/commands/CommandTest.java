package commands;

import client.ClientConnection;
import exceptions.ExitException;
import exceptions.IncorrectInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import parsers.MyScanner;
import settings.Settings;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    private final MyScanner console = new MyScanner(System.in);
    private ClientConnection conn;

    @BeforeEach
    void init() {
        conn = Mockito.mock(ClientConnection.class);
        Mockito.when(conn.sendReqGetResp(Mockito.anyString(), Mockito.any())).thenReturn("Some response");
    }

    @Test
    void changePassword() {
        Command command = new ChangePasswordCommand(conn);
        assertEquals("", command.execute("lol", console));
        assertEquals("Some response", command.execute("change_password 123", console));
    }

    @Test
    void exit() {
        Command exit = new ExitCommand(conn);
        assertThrows(ExitException.class, () -> exit.execute("", console));
    }

    @Test
    void help() {
        Command help = new HelpCommand(conn);
        assertTrue(help.execute("help", console).endsWith("""
                \tsign_out : sign out from the account
                \texit : terminate the program
                \texecute_script filepath : execute script in the file, by its filepath"""));
    }

    @Test
    void ping() {
        Command ping = new PingCommand(conn);
        assertTrue(ping.execute("ping", console).startsWith("Some response"));
    }

    @Test
    void signOut() {
        Command signOut = new SignOutCommand(conn);
        Mockito.when(conn.getSettings()).thenReturn(new Settings());
        assertEquals("", signOut.execute("sign_out", console));
        Mockito.doThrow(RuntimeException.class).when(conn).setUser();
        assertThrows(RuntimeException.class, () -> signOut.execute("sign_out", console));
    }

    @Test
    void update() {
        Command update = new UpdateCommand(conn);
        assertEquals("Some response", update.execute("update 6", console));
        Mockito.when(conn.sendReqGetResp(Mockito.anyString(), Mockito.any())).thenReturn("No such element");
        assertEquals("No such element", update.execute("update", console));
    }

    @Test
    void executeScript() {
        Mockito.when(conn.getProcessor()).thenReturn(new CommandProcessor(conn));
        String script1 = CommandTest.class.getClassLoader().getResource("script1.txt").getPath();
        String exit_script = CommandTest.class.getClassLoader().getResource("exitScript.txt").getPath();
        Command executeScript = new ExecuteScriptCommand(conn);
        assertThrows(IncorrectInputException.class, () -> executeScript.execute("", console));
        assertEquals("", executeScript.execute("execute_script " + script1, console));
        assertThrows(ExitException.class, () -> executeScript.execute("execute_script " + exit_script, console));
    }
}