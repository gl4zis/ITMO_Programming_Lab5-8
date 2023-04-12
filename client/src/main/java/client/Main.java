package client;

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
            System.setProperty("logs.path", "C:/Windows/Temp/lab5-8-server-logs/" + creationDate + ".log");
        else
            System.setProperty("logs.path", "/tmp/lab5-8-server-logs/" + creationDate + ".log");
    }

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    /**
     * Opens connection, on localhost and fixed port
     */
    public static void main(String[] args) {
        LOGGER.debug("Client startup");
        try {
            InetAddress host = InetAddress.getByName("localhost");
            int port = 9812;
            Connection connection = new Connection(host, port);
            LOGGER.info(String.format("client.Connection parameters. Host: %s, port: %d", host, port));
            connection.run();
            LOGGER.debug("Correct exit");
        } catch (Throwable e) {
            LOGGER.fatal("Something very strange happened =0 " + e.getMessage());
            LOGGER.debug("Incorrect exit (client crashed)");
        }
    }
}