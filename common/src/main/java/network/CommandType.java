package network;

import exceptions.IncorrectInputException;

import java.io.Serializable;

public enum CommandType implements Serializable {
    ADD("add", false, null, true),
    ADD_IF_MIN("add_if_min", false, null, true),
    AVERAGE_OF_WEIGHT("average_of_weight", false, null, false),
    CLEAR("clear", false, null, false),
    EXECUTE_SCRIPT("execute_script", true, String.class, false),
    EXIT("exit", false, null, false),
    FILTER_LESS_THAN_WEIGHT("filter_less_than_weight", true, long.class, false),
    HELP("help", false, null, false),
    INFO("info", false, null, false),
    MIN_BY_AGE("min_by_age", false, null, false),
    REMOVE_BY_ID("remove_by_id", true, int.class, false),
    REMOVE_GREATER("remove_greater", false, null, true),
    REMOVE_LOWER("remove_lower", false, null, true),
    SHOW("show", false, null, false),
    SAVE("save", false, null, false),
    UPDATE("update", true, int.class, true);

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

    public static CommandType getByName(String name) {
        for (CommandType command : CommandType.values()) {
            if (command.name.equals(name)) {
                return command;
            }
        }
        throw new IncorrectInputException("Unknown command");
    }

    public boolean isHaveArgs() {
        return haveArgs;
    }

    public Class argClass() {
        return argClass;
    }

    public boolean isNeedReadDragon() {
        return needReadDragon;
    }

    @Override
    public String toString() {
        return name;
    }
}
