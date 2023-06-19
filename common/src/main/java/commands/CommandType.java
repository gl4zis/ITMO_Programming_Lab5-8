package commands;

import exceptions.IncorrectInputException;

import java.io.Serializable;

/**
 * Types of all commands, what should be sent on the server.
 * Includes command name, having argument, its type and having need of read dragon
 */
public enum CommandType implements Serializable {
    ADD("add", false, null, true, true),
    ADD_IF_MIN("add_if_min", false, null, true, true),
    AVERAGE_OF_WEIGHT("average_of_weight", false, null, false, true),
    CLEAR("clear", false, null, false, true),
    FILTER_LESS_THAN_WEIGHT("filter_less_than_weight", true, long.class, false, true),
    HELP("help", false, null, false, false),
    INFO("info", false, null, false, true),
    MIN_BY_AGE("min_by_age", false, null, false, true),
    REMOVE_BY_ID("remove_by_id", true, int.class, false, true),
    REMOVE_GREATER("remove_greater", false, null, true, true),
    REMOVE_LOWER("remove_lower", false, null, true, true),
    SHOW("show", false, null, false, false),
    UPDATE("update", true, int.class, true, true),
    FIND("find", true, int.class, false, false),
    PING("ping", false, null, false, false),
    SIGN_UP("sign_up", false, null, false, false),
    SIGN_IN("sign_in", false, null, false, false),
    CHANGE_PASSWORD("change_password", true, String.class, false, false);

    private final String name;
    private final boolean haveArgs;
    private final Class argClass;
    private final boolean needReadDragon;
    private final boolean selfButton;

    CommandType(String name, boolean haveArgs, Class argClass, boolean needReadDragon, boolean selfButton) {
        this.name = name;
        this.haveArgs = haveArgs;
        this.argClass = argClass;
        this.needReadDragon = needReadDragon;
        this.selfButton = selfButton;
    }

    /**
     * Returns commandType objects, by its name
     *
     * @param name string name of command
     * @return commandType
     */
    public static CommandType getByName(String name) {
        for (CommandType command : CommandType.values()) {
            if (command.name.equals(name)) {
                return command;
            }
        }
        throw new IncorrectInputException("Unknown command");
    }

    public boolean haveSelfButton() {
        return selfButton;
    }

    public String getName() {
        return name;
    }

    /**
     * Flag, signals if this command must have argument
     */
    public boolean isHaveArgs() {
        return haveArgs;
    }

    /**
     * Class of command argument. If command have no argument, returns null
     */
    public Class getArgClass() {
        return argClass;
    }

    /**
     * Flag, signals if this command must read new dragon object
     */
    public boolean isNeedReadDragon() {
        return needReadDragon;
    }

    /**
     * Returns string name of command
     */
    @Override
    public String toString() {
        return name;
    }
}
