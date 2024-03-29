package commands;

import network.Request;

import java.util.stream.Collectors;

/**
 * Non-argument command "help". Outputs info about all commands
 */
public class HelpCommand extends Command {
    private final CommandManager commandManager;

    /**
     * Constructor, sets command manager, that the command works with, description of command
     */
    HelpCommand(CommandManager commandManager) {
        super("help : display help for available commands");
        this.commandManager = commandManager;
    }

    /**
     * Outputs info about all commands loaded in command manager
     */
    @Override
    public String execute(Request request) {
        return commandManager.getCommands().stream()
                .map(Command::getDescription)
                .filter((p) -> !p.isEmpty())
                .collect(Collectors.joining("\n\t", "\t", ""));
    }
}