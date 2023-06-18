package client;

import GUI.MyFrame;
import general.OsUtilus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.Settings;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

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
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

            SwingUtilities.invokeLater(Settings::new);

        } catch (Exception e) {
            LOGGER.fatal("Something very strange happened =0 " + e.getMessage());
            LOGGER.debug("Incorrect exit (client crashed)");
        }
    }
}