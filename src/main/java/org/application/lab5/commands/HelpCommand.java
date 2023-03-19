package org.application.lab5.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.application.lab5.parsers.InputScriptReader;

/**
 * Non-argument command "help". Outputs info about all commands
 */
public class HelpCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(HelpCommand.class);
    private final CommandManager commandManager;

    /** Constructor, sets command manager, that the command works with, name and description of command
     */
    HelpCommand(CommandManager commandManager) {
        super("help", "help : вывести справку по доступным командам");
        this.commandManager = commandManager;
    }

    /** Outputs info about all commands loaded in command manager
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public void execute(InputScriptReader reader) {
        for (Command command : commandManager.getCommands()) {
            System.out.println("\t" + command.getDescription());
        }
        LOGGER.debug("Help command was successfully executed");
    }
}