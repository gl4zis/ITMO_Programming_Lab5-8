package commands;

import collection.DragonCollection;
import network.Request;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for finding command class by its name.
 * (In console line or in script file line)
 */
public class CommandManager {
    private final DragonCollection collection;
    private final Connection baseConn;
    private Map<String, Command> commands;

    /**
     * Constructor, sets database Connection and DragonCollection with which commands will work
     */
    public CommandManager(Connection baseConn, DragonCollection collection) {
        this.collection = collection;
        this.baseConn = baseConn;
        addAllCommands();
    }

    /**
     * Creates objects of all commands and adds it to the HashMap (commandName, command).
     * Constructor of command class need to get only CommandManager !!
     */
    @SuppressWarnings("unchecked")
    private void addAllCommands() {
        commands = new HashMap<>();
        for (CommandType type : CommandType.values()) {
            String className = parseCommandName(type.getName());
            try {
                Class<? extends Command> commandClass = (Class<? extends Command>) Class.forName(className);
                Command command = commandClass.getDeclaredConstructor(CommandManager.class).newInstance(this);
                commands.put(type.getName(), command);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException ignored) {
            }
        }
    }

    /**
     * Parsing commands' name.
     * Uses enum CommandType for gets list of all server commands.
     * Command class needs to be named in upper camel case with postfix 'Command'.
     * Command name (in CommandType) needs to be in lower snake case.
     * For example: 'add_if_min' is name of 'AddIfMinCommand' class
     *
     * @param name command name for parsing
     * @return name of this command class
     */
    private String parseCommandName(String name) {
        char[] letters = name.toCharArray();
        char lastLetter = ' ';
        StringBuilder className = new StringBuilder("commands.");
        for (char a : letters) {
            if (lastLetter == '_' || lastLetter == ' ') {
                className.append(Character.toUpperCase(a));
            } else if (a != '_') {
                className.append(a);
            }
            lastLetter = a;
        }
        className.append("Command");
        return className.toString();
    }

    /**
     * Processes request. Finds command and executes it
     *
     * @return response of command executing
     */
    public String seekCommand(Request request) {
        CommandType commandType = request.command();
        String command = commandType.toString();
        return commands.get(command).execute(request);
    }

    public Collection<Command> getCommands() {
        return commands.values();
    }

    public DragonCollection getCollection() {
        return collection;
    }

    public Connection getConn() {
        return baseConn;
    }
}