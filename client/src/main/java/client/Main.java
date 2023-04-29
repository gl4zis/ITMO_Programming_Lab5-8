package client;

import exceptions.ExitException;
import general.OsUtilus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    // Sets system properties for logger configuration and for saving log files
    static {
        String creationDate = new SimpleDateFormat("yyyy-MM-dd/HH-mm-ss").format(new Date());
        if (OsUtilus.IsWindows())
            System.setProperty("logs.path", "C:/Windows/Temp/Logs(5-8)/client/" + creationDate + ".log");
        else
            System.setProperty("logs.path", "/tmp/Logs(5-8)/client/" + creationDate + ".log");
    }

    /**
     * Opens connection, on localhost and fixed port
     */
    public static void main(String[] args) {
        Logger LOGGER = LogManager.getLogger(Main.class);
        LOGGER.debug("Client startup");
        try {
            InetAddress host = InetAddress.getByName("localhost");
            int port = 9812;
            LOGGER.info(String.format("client.Connection parameters. Host: %s, port: %d", host, port));

            ClientConnection connection = new ClientConnection(host, port);
            connection.run();
        } catch (ExitException e) {
            LOGGER.debug("Correct exit");
        } catch (Throwable e) {
            LOGGER.fatal("Something very strange happened =0 " + e.getMessage());
            LOGGER.debug("Incorrect exit (client crashed)");
        }
    }
}