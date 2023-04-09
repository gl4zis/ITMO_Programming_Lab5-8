package commands;

import dragons.Dragon;
import exceptions.IncorrectInputException;
import parsers.InputScriptReader;

/**
 * Abstract class which are extending all commands classes
 */
public abstract class Command {
    private final String name;
    private final String descr;

    /**
     * Constructor, sets name and description of this command
     */
    protected Command(String name, String descr) {
        this.name = name;
        this.descr = descr;
    }

    /**
     * Returns name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns description of command for "help" command
     *
     * @return description
     */
    public String getDescription() {
        return descr;
    }

    /**
     * Execute method for commands without arguments
     *
     * @param reader reader of file from that gives data, if null data gives from console
     * @throws IncorrectInputException if there is no such non-argument command
     */
    public abstract String execute(InputScriptReader reader);

    public String execute(InputScriptReader reader, Dragon dragon) {
        return execute(reader);
    }

    /**
     * Execute method for commands with arguments
     *
     * @param reader reader of file from that gives data, if null data gives from console
     * @param arg    command argument
     * @throws IncorrectInputException if there is no such argument command
     */
    public abstract String execute(InputScriptReader reader, String arg);

    public String execute(InputScriptReader reader, String arg, Dragon dragon) {
        return execute(reader, arg);
    }
}
