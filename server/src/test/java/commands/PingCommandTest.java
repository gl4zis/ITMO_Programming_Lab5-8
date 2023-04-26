package commands;

import network.Request;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class PingCommandTest {
    @Test
    void execute() throws UnknownHostException {
        String ping = new PingCommand().execute(new Request(CommandType.PING, null, null));
        assertEquals("Connected to server: " + InetAddress.getLocalHost(), ping);
    }
}