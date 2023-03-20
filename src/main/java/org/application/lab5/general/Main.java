package org.application.lab5.general;

import org.apache.logging.log4j.LogManager;
import org.application.lab5.collection.CollectionManager;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.commands.CommandManager;
import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.parsers.JsonManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Main class, that starts the application
 */

public class Main {

    // Sets system properties for logger configuration and for saving log files
    static {
        String creationDate = new SimpleDateFormat("yyyy-MM-dd/HH-mm-ss").format(new Date());
        System.setProperty("logs.path", "./lab5-dev-logs/" + creationDate + ".log");
        /*String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String userName;
        if (OsUtilus.IsWindows()) userName = System.getenv("USERNAME");
        else userName = System.getenv("USER");
        String dirPath = String.join("/", Arrays.stream(jarPath.split("/")).toList().subList(0, jarPath.split("/").length - 4));  //For debugging
        String dirPath = String.join("/", Arrays.stream(jarPath.split("/")).toList().subList(0, jarPath.split("/").length - 1));  //For jar executing
        System.setProperty("logs.path", dirPath + "/logs/" + userName + "/" + creationDate + ".log");

        Так не получится без выдачи всем прав на запись в каталог, где находится jar =(
         */
    }

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(Main.class);

    /** Executable method, that starts the application.
     * Creates all necessary objects (JsonManager, DragonCollection, CommandManager),
     * Fills out collection from JSON file and waits for command input in console line
     */
    public static void main(String[] args) {
        LOGGER.debug("Application startup");
        try {
            JsonManager jsonManager;
            if (args.length == 0) {
                LOGGER.error("There is no command-line argument");
                jsonManager = new JsonManager("");
            } else jsonManager = new JsonManager(args[0]);
            DragonCollection collection = new DragonCollection();
            CommandManager commandManager = new CommandManager(jsonManager, collection);
            CollectionManager.uploadCollection(jsonManager.readJSON(), collection);
            while (true) {
                System.out.print("-> ");
                commandManager.seekCommand(InputConsoleReader.readNextLine());
            }
        } catch (Throwable e) {
            LOGGER.fatal("Incorrect exit from app (crash): " + e.getMessage());
        }
    }
}