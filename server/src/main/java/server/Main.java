package server;


import collection.DragonCollection;
import commands.CommandManager;
import database.DataBaseParser;
import database.MyBaseConnection;
import exceptions.ExitException;
import general.OsUtilus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    // Sets system properties for logger configuration and for saving log files
    static {
        String creationDate = new SimpleDateFormat("yyyy-MM-dd/HH-mm-ss").format(new Date());
        if (OsUtilus.IsWindows())
            System.setProperty("logs.path", "C:/Windows/Temp/Logs(5-8)/server/" + creationDate + ".log");
        else
            System.setProperty("logs.path", "/tmp/Logs(5-8)/server/" + creationDate + ".log");
    }

    /**
     * Creates managers, creates collection, loads it from JSON and opens connection on the fixed port
     */
    public static void main(String[] args) {
        Logger LOGGER = LogManager.getLogger(Main.class);
        LOGGER.debug("Server startup");
        try {
            DragonCollection collection = new DragonCollection();
            Connection baseConn = MyBaseConnection.connect();
            DataBaseParser.uploadCollection(baseConn, collection);
            CommandManager manager = new CommandManager(baseConn, collection);

            ServerConnection con = new ServerConnection(manager);
            int port = 9812;
            LOGGER.info("Waiting connection on port: " + port);
            con.open(port);

        } catch (ExitException e) {
            LOGGER.debug("Correct exit");
        } catch (Throwable e) {
            e.printStackTrace();
            LOGGER.fatal("Something very strange happened =0 " + e.getMessage());
            LOGGER.debug("Incorrect exit (server crashed)");
        }
    }
}
