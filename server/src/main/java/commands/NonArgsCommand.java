package commands;

import dragons.Dragon;
import exceptions.IncorrectInputException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.InputScriptReader;

/**
 * Class for commands, without arguments in its line.
 * Example: "add". Have no arguments, despite need to type dragon in next 8 lines
 */
public abstract class NonArgsCommand extends Command {
    private static final Logger LOGGER = LogManager.getLogger(ArgsCommand.class);

    /**
     * Standard constructor
     */
    protected NonArgsCommand(String name, String descr) {
        super(name, descr);
    }

    /**
     * Execute method for all commands, extending this class
     *
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public abstract String execute(InputScriptReader reader);

    /**
     * Wrong execute method.
     * Needs to not execute something like that: "add 33"
     * There are arguments in line with non-arg command
     */
    @Override
    public String execute(InputScriptReader reader, String arg) throws IncorrectInputException {
        LOGGER.warn(CommandManager.UNKNOWN_COMMAND);
        return CommandManager.UNKNOWN_COMMAND;
    }

    public String execute(InputScriptReader reader, String arg, Dragon dragon) throws IncorrectInputException {
        return execute(reader, arg);
    }
}
