package network;

import exceptions.IncorrectInputException;

import java.io.Serializable;

/**
 * Types of all commands, what should be sent on the server.
 * Includes command name, having argument, its type and having need of read dragon
 */
public enum CommandType implements Serializable {
    ADD("add", false, null, true),
    ADD_IF_MIN("add_if_min", false, null, true),
    AVERAGE_OF_WEIGHT("average_of_weight", false, null, false),
    CLEAR("clear", false, null, false),
    FILTER_LESS_THAN_WEIGHT("filter_less_than_weight", true, long.class, false),
    HELP("help", false, null, false),
    INFO("info", false, null, false),
    MIN_BY_AGE("min_by_age", false, null, false),
    REMOVE_BY_ID("remove_by_id", true, int.class, false),
    REMOVE_GREATER("remove_greater", false, null, true),
    REMOVE_LOWER("remove_lower", false, null, true),
    SHOW("show", false, null, false),
    UPDATE("update", true, int.class, true),
    FIND("find", true, int.class, false),
    PING("ping", false, null, false);

    private final String name;
    private final boolean haveArgs;
    private final Class argClass;
    private final boolean needReadDragon;

    CommandType(String name, boolean haveArgs, Class argClass, boolean needReadDragon) {
        this.name = name;
        this.haveArgs = haveArgs;
        this.argClass = argClass;
        this.needReadDragon = needReadDragon;
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

    /**
     * Flag, signals if this command must have argument
     */
    public boolean isHaveArgs() {
        return haveArgs;
    }

    /**
     * Class of command argument. If command have no argument, returns null
     */
    public Class argClass() {
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
