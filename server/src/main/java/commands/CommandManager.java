package commands;

import collection.DragonCollection;
import network.Request;
import parsers.JsonManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for finding command by its name.
 * (In console line or in script file line)
 */
public class CommandManager {
    private final JsonManager json;
    private final DragonCollection collection;
    private Map<String, Command> commands;

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
            Command filterLessThanWeight = new FilterLessThanWeightCommand(collection);
            Command help = new HelpCommand(this);
            Command info = new InfoCommand(collection);
            Command minByAge = new MinByAgeCommand(collection);
            Command removeById = new RemoveByIdCommand(collection);
            Command removeGreater = new RemoveGreaterCommand(collection);
            Command removeLower = new RemoveLowerCommand(collection);
            Command show = new ShowCommand(collection);
            Command update = new UpdateCommand(collection);
            Command find = new FindCommand(collection);
            Command ping = new PingCommand();
            commands.put(add.getName(), add);
            commands.put(addIfMin.getName(), addIfMin);
            commands.put(averageOfWeight.getName(), averageOfWeight);
            commands.put(clear.getName(), clear);
            commands.put(filterLessThanWeight.getName(), filterLessThanWeight);
            commands.put(help.getName(), help);
            commands.put(info.getName(), info);
            commands.put(minByAge.getName(), minByAge);
            commands.put(removeById.getName(), removeById);
            commands.put(removeGreater.getName(), removeGreater);
            commands.put(removeLower.getName(), removeLower);
            commands.put(show.getName(), show);
            commands.put(update.getName(), update);
            commands.put(find.getName(), find);
            commands.put(ping.getName(), ping);
        }
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
     * @return JsonManager
     */
    public JsonManager getJson() {
        return json;
    }

    /**
     * @return Collection of dragons
     */
    public DragonCollection getCollection() {
        return collection;
    }
}