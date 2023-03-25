package org.application.lab5.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.exceptions.IncorrectInputException;
import org.application.lab5.parsers.InputScriptReader;
import org.application.lab5.parsers.JsonManager;

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

    /**
     * Constructor, sets JsonManager and DragonCollection with which commands will work
     */
    public CommandManager(JsonManager json, DragonCollection collection) {
        addStandardCommands(json, collection);
    }

    /**
     * Creates objects of standard command and adds it to the map
     *
     * @param jsonManager JsonManager, that commands will work with
     * @param collection  DragonCollection, that commands will work with
     */
    private void addStandardCommands(JsonManager jsonManager, DragonCollection collection) {
        if (commands == null) {
            commands = new HashMap<>();
            Command add = new AddCommand(collection);
            Command addIfMin = new AddIfMinCommand(collection);
            Command averageOfWeight = new AverageOfWeightCommand(collection);
            Command clear = new ClearCommand(collection);
            Command executeScript = new ExecuteScriptCommand(this);
            Command exit = new ExitCommand(collection);
            Command filterLessThanWeight = new FilterLessThanWeightCommand(collection);
            Command help = new HelpCommand(this);
            Command info = new InfoCommand(collection);
            Command minByAge = new MinByAgeCommand(collection);
            Command removeById = new RemoveByIdCommand(collection);
            Command removeGreater = new RemoveGreaterCommand(collection);
            Command removeLower = new RemoveLowerCommand(collection);
            Command save = new SaveCommand(jsonManager, collection);
            Command show = new ShowCommand(collection);
            Command sort = new SortCommand(collection);
            Command update = new UpdateCommand(collection);
            commands.put(add.getName(), add);
            commands.put(addIfMin.getName(), addIfMin);
            commands.put(averageOfWeight.getName(), averageOfWeight);
            commands.put(clear.getName(), clear);
            commands.put(executeScript.getName(), executeScript);
            commands.put(exit.getName(), exit);
            commands.put(filterLessThanWeight.getName(), filterLessThanWeight);
            commands.put(help.getName(), help);
            commands.put(info.getName(), info);
            commands.put(minByAge.getName(), minByAge);
            commands.put(removeById.getName(), removeById);
            commands.put(removeGreater.getName(), removeGreater);
            commands.put(removeLower.getName(), removeLower);
            commands.put(save.getName(), save);
            commands.put(show.getName(), show);
            commands.put(sort.getName(), sort);
            commands.put(update.getName(), update);
        }
    }

    /**
     * Finds command in input string line and executes it.
     * Reader needs to transmit it to Command.execute for availability executing it from script
     *
     * @param reader reader of file from that gives data, if null data gives from console
     * @param line   string input, where command will be seeking
     * @throws IncorrectInputException if no such command in the line
     */
    public void seekCommand(InputScriptReader reader, String line) {
        if (!line.trim().isEmpty()) {
            String[] input = line.split(" ");
            if (input.length > 2)
                LOGGER.warn(line + " - " + UNKNOWN_COMMAND);
            else {
                String command = input[0];
                if (commands.containsKey(command)) {
                    try {
                        String arg = input[1];
                        commands.get(command).execute(reader, arg);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        commands.get(command).execute(reader);
                    }
                } else {
                    LOGGER.warn(line + " - " + UNKNOWN_COMMAND);
                }
            }
        }
    }

    /**
     * Executes seekCommand with reader = null
     *
     * @param line string input, where command will be seeking
     */
    public void seekCommand(String line) {
        seekCommand(null, line);
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
}