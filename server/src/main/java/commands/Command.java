package commands;

import network.Request;

/**
 * Abstract class which are extending all commands classes
 */
public abstract class Command {
    private final String descr;

    /**
     * Constructor sets description of this command
     */
    protected Command(String descr) {
        this.descr = descr;
    }

    public String getDescription() {
        return descr;
    }

    /**
     * Standard execute method of this command.
     * Gets all needed information from request
     */
    public abstract String execute(Request request);
}
