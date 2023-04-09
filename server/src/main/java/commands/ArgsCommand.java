package commands;

import dragons.Dragon;
import exceptions.IncorrectInputException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.InputScriptReader;

/**
 * Class for commands, having arguments in its line.
 * Example: "update 33". update - command name, 33 - argument
 */
public abstract class ArgsCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(ArgsCommand.class);

    /**
     * Standard constructor
     */
    protected ArgsCommand(String name, String descr) {
        super(name, descr);
    }

    /**
     * Execute method for all commands, which extending this class
     *
     * @param reader reader of file from that gives data, if null data gives from console
     * @param arg    argument of command
     */
    @Override
    public abstract String execute(InputScriptReader reader, String arg);

    /**
     * Wrong Execute method.
     * Needs to not execute some like this: "update" with null argument
     */
    @Override
    public String execute(InputScriptReader reader) throws IncorrectInputException {
        LOGGER.warn(CommandManager.UNKNOWN_COMMAND);
        return CommandManager.UNKNOWN_COMMAND;
    }

    @Override
    public String execute(InputScriptReader reader, Dragon dragon) throws IncorrectInputException {
        return execute(reader);
    }
}
