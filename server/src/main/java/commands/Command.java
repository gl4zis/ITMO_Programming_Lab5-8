package commands;

import dragons.Dragon;
import exceptions.IncorrectInputException;

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
     * @throws IncorrectInputException if there is no such non-argument command
     */
    public abstract String execute();

    public String execute(Dragon dragon) {
        return execute();
    }

    /**
     * Execute method for commands with arguments
     *
     * @param arg command argument
     * @throws IncorrectInputException if there is no such argument command
     */
    public abstract String execute(String arg);

    public String execute(String arg, Dragon dragon) {
        return execute(arg);
    }
}
