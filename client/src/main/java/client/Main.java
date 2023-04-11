package client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    // Sets system properties for logger configuration and for saving log files
    static {
        String creationDate = new SimpleDateFormat("yyyy-MM-dd/HH-mm-ss").format(new Date());
        System.setProperty("logs.path", "./lab5-8-client-logs/" + creationDate + ".log");
    }

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.debug("Client startup");
        try {
            InetAddress host = InetAddress.getByName("localhost");
            int port = 8812;
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