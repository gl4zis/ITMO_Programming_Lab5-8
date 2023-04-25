package server;


import collection.CollectionManager;
import collection.DragonCollection;
import commands.CommandManager;
import exceptions.ExitException;
import general.OsUtilus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.JsonManager;

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
     * Creates managers, creates collection, loads it from JSON and opens connection on the fixed port
     */
    public static void main(String[] args) {
        LOGGER.debug("Server startup");
        try {
            JsonManager jsonManager;
            if (args.length == 0) {
                jsonManager = new JsonManager("");
            } else jsonManager = new JsonManager(args[0]);
            DragonCollection collection = new DragonCollection();
            CommandManager commandManager = new CommandManager(jsonManager, collection);
            CollectionManager.uploadCollection(jsonManager.readJSON(), collection);
            Connection con = new Connection(commandManager);
            int port = 9812;
            LOGGER.info("Waiting connection on port: " + port);
            con.open(port);
        } catch (ExitException e) {
            LOGGER.debug("Correct exit");
        } catch (Throwable e) {
            LOGGER.fatal("Something very strange happened =0 " + e.getMessage());
            LOGGER.debug("Incorrect exit (server crashed)");
        }
    }
}
