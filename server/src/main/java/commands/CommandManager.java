package commands;

import collection.DragonCollection;
import dragons.Dragon;
import network.CommandType;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import parsers.JsonManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class for finding command by its name.
 * (In console line or in script file line)
 */
public class CommandManager {
    static final String UNKNOWN_COMMAND = "Unknown command. Type \"help\" for see information about commands";
    private static final Logger LOGGER = LogManager.getLogger(CommandManager.class);
    private Map<String, Command> commands;
    private final JsonManager json;
    private final DragonCollection collection;

    /**
     * Constructor, sets JsonManager and DragonCollection with which commands will work
     */
    public CommandManager(JsonManager json, DragonCollection collection) {
        this.json = json;
        this.collection = collection;
        addStandardCommands();
    }

    /**
     * Creates objects of standard command and adds it to the map
     */
    private void addStandardCommands() {
        if (commands == null) {
            commands = new HashMap<>();
            Command add = new AddCommand(collection);
            Command addIfMin = new AddIfMinCommand(collection);
            Command averageOfWeight = new AverageOfWeightCommand(collection);
            Command clear = new ClearCommand(collection);
            Command exit = new ExitCommand();
            Command filterLessThanWeight = new FilterLessThanWeightCommand(collection);
            Command help = new HelpCommand(this);
            Command info = new InfoCommand(collection);
            Command minByAge = new MinByAgeCommand(collection);
            Command removeById = new RemoveByIdCommand(collection);
            Command removeGreater = new RemoveGreaterCommand(collection);
            Command removeLower = new RemoveLowerCommand(collection);
            Command show = new ShowCommand(collection);
            Command update = new UpdateCommand(collection);
            commands.put(add.getName(), add);
            commands.put(addIfMin.getName(), addIfMin);
            commands.put(averageOfWeight.getName(), averageOfWeight);
            commands.put(clear.getName(), clear);
            commands.put(exit.getName(), exit);
            commands.put(filterLessThanWeight.getName(), filterLessThanWeight);
            commands.put(help.getName(), help);
            commands.put(info.getName(), info);
            commands.put(minByAge.getName(), minByAge);
            commands.put(removeById.getName(), removeById);
            commands.put(removeGreater.getName(), removeGreater);
            commands.put(removeLower.getName(), removeLower);
            commands.put(show.getName(), show);
            commands.put(update.getName(), update);
        }
    }

    public String seekCommand(Request request) {
        CommandType commandType = request.getCommand();
        String command = commandType.toString();
        String arg = request.getArg();
        Dragon dragon = request.getDragon();
        if (!commandType.isNeedReadDragon()) {
            if (commandType.isHaveArgs())
                return commands.get(command).execute(arg);
            else
                return commands.get(command).execute();
        } else {
            if (commandType.isHaveArgs())
                return commands.get(command).execute(arg, dragon);
            else
                return commands.get(command).execute(dragon);
        }
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
     * Returns set with command names
     *
     * @return names
     */
    public Set<String> getCommandNames() {
        return commands.keySet();
    }

    /**
     * Adds new non-standard command in the map
     *
     * @param newCommand object of command, that will be added in the map
     */
    public void addNewCommand(ArgsCommand newCommand) {
        commands.put(newCommand.getName(), newCommand);
    }

    public JsonManager getJson() {
        return json;
    }

    public DragonCollection getCollection() {
        return collection;
    }
}