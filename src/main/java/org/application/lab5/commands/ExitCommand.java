package org.application.lab5.commands;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.parsers.InputScriptReader;

/**
 * Non-argument command "exit". Close the application without save changes
 */
public class ExitCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(ExitCommand.class);
    private final DragonCollection collection;

    /**
     * Standard constructor, sets collection,  name and description of command
     */
    ExitCommand(DragonCollection collection) {
        super("exit", "exit : завершить программу (без сохранения в файл)");
        this.collection = collection;
    }

    /** Closes this app
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public void execute(InputScriptReader reader) {
        if (collection.saved) {
            LOGGER.debug("Correct exit");
            System.exit(0);
        } else {
            LOGGER.warn("Trying to exit without saving!");
            System.out.print("Type 'yes' or 'y' for confirm exit: ");
            String confirmLine = InputConsoleReader.readNextLine();
            if (confirmLine.equals("yes") || confirmLine.equals("y")) {
                LOGGER.debug("Correct exit without saving collection");
                System.exit(0);
            } else System.out.println("You was returned to app command-line");
        }
        LOGGER.debug("Exit command was executed successfully (without ending app)");
    }
}