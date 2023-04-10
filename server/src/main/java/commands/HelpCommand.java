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
        super("help", "help : display help for available commands");
        this.commandManager = commandManager;
    }

    /**
     * Outputs info about all commands loaded in command manager
     */
    @Override
    public String execute() {
        StringBuilder output = new StringBuilder();
        for (Command command : commandManager.getCommands()) {
            output.append('\t').append(command.getDescription()).append('\n');
        }
        output.append("exit : terminate the program");
        return output.toString();
    }
}