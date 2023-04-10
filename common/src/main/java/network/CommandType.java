package network;

import exceptions.IncorrectInputException;

import java.io.Serializable;

public enum CommandType implements Serializable {
    ADD("add", false, true),
    ADD_IF_MIN("add_if_min", false, true),
    AVERAGE_OF_WEIGHT("average_of_weight", false, false),
    CLEAR("clear", false, false),
    EXECUTE_SCRIPT("execute_script", true, false),
    EXIT("exit", false, false),
    FILTER_LESS_THAN_WEIGHT("filter_less_than_weight", true, false),
    HELP("help", false, false),
    INFO("info", false, false),
    MIN_BY_AGE("min_by_age", false, false),
    REMOVE_BY_ID("remove_by_id", true, false),
    REMOVE_GREATER("remove_greater", false, true),
    REMOVE_LOWER("remove_lower", false, true),
    SHOW("show", false, false),
    SAVE("save", false, false),
    UPDATE("update", true, true);

    private final String name;
    private final boolean haveArgs;
    private final boolean needReadDragon;

    CommandType(String name, boolean haveArgs, boolean needReadDragon) {
        this.name = name;
        this.haveArgs = haveArgs;
        this.needReadDragon = needReadDragon;
    }

    public boolean isHaveArgs() {
        return haveArgs;
    }

    public boolean isNeedReadDragon() {
        return needReadDragon;
    }

    @Override
    public String toString() {
        return name;
    }

    public static CommandType getByName(String name) {
        for (CommandType command : CommandType.values()) {
            if (command.name.equals(name)) {
                return command;
            }
        }
        throw new IncorrectInputException("Unknown command");
    }
}
