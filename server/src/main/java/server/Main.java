package server;


import collection.DragonCollection;
import commands.CommandManager;
import database.DataBaseManager;
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
        try (Connection baseConn = MyBaseConnection.connect()) {
            DragonCollection collection = new DragonCollection();
            DataBaseManager baseMan = new DataBaseManager(baseConn);
            baseMan.uploadCollection(collection);
            CommandManager manager = new CommandManager(baseMan, collection);

            int port = 9812;
            ServerConnection con = new ServerConnection(manager);
            con.open(port);
        } catch (ExitException e) {
            LOGGER.debug("Correct exit");
            System.out.println(e.getMessage());
        } catch (Throwable e) {
            LOGGER.fatal("Something very strange happened =0 " + e.getMessage());
            LOGGER.debug("Incorrect exit (server crashed)");
        }
    }
}
