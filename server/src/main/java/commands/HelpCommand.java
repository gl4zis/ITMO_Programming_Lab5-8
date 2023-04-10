package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Non-argument command "help". Outputs info about all commands
 */
public class HelpCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(HelpCommand.class);
    private final CommandManager commandManager;

    /**
     * Constructor, sets command manager, that the command works with, name and description of command
     */
    HelpCommand(CommandManager commandManager) {
        super("help", "help : вывести справку по доступным командам");
        this.commandManager = commandManager;
    }

    /**
     * Outputs info about all commands loaded in command manager
     */
    @Override
    public String execute() {
        StringBuilder line = new StringBuilder();
        int counter = 0;
        for (Command command : commandManager.getCommands()) {
            line.append("\t").append(command.getDescription());
            if (++counter < commandManager.getCommands().size())
                line.append("\n");
        }
        LOGGER.debug("Help command was successfully executed");
        return line.toString();
    }
}