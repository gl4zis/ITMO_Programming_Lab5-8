package commands;

import collection.DragonCollection;
import network.Request;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for finding command by its name.
 * (In console line or in script file line)
 */
public class CommandManager {
    private final DragonCollection collection;
    private final Connection baseConn;
    private Map<String, Command> commands;

    /**
     * Constructor, sets JsonManager and DragonCollection with which commands will work
     */
    public CommandManager(Connection baseConn, DragonCollection collection) {
        this.collection = collection;
        this.baseConn = baseConn;
        addStandardCommands();
    }

    /**
     * Creates objects of standard command and adds it to the map
     */
    @SuppressWarnings("unchecked")
    private void addStandardCommands() {
        commands = new HashMap<>();
        for (CommandType type : CommandType.values()) {
            String className = parseCommandName(type.getName());
            try {
                Class<? extends Command> commandClass = (Class<? extends Command>) Class.forName(className);
                Command command = commandClass.getDeclaredConstructor(CommandManager.class).newInstance(this);
                commands.put(command.getName(), command);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException ignored) {
            }
        }
    }

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
     */
    public String seekCommand(Request request) {
        CommandType commandType = request.command();
        String command = commandType.toString();
        return commands.get(command).execute(request);
    }

    /**
     * Returns collection with command objects
     *
     * @return commands
     */
    public Collection<Command> getCommands() {
        return commands.values();
    }

    /**
     * @return Collection of dragons
     */
    public DragonCollection getCollection() {
        return collection;
    }

    public Connection getConn() {
        return baseConn;
    }
}