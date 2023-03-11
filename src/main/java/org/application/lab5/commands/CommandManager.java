package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.exceptions.IncorrectInputException;
import org.application.lab5.parsers.InputScriptReader;
import org.application.lab5.parsers.JsonManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandManager {

    private Map<String, Command> commands;

    public CommandManager(JsonManager json, DragonCollection collection) {
        addStandartCommands(json, collection);
    }

    private void addStandartCommands(JsonManager jsonManager, DragonCollection collection) {
        if (commands == null) {
            commands = new HashMap<>();
            Command add = new AddCommand(collection);
            Command addIfMin = new AddIfMinCommand(collection);
            Command averageOfWeight = new AverageOfWeightCommand(collection);
            Command clear = new ClearCommand(collection);
            Command executeScript = new ExecuteScriptCommand(this);
            Command exit = new ExitCommand();
            Command filterLessThanWeight = new FilterLessThanWeightCommand(collection);
            Command help = new HelpCommand(this);
            Command info = new InfoCommand(collection);
            Command minByAge = new MinByAgeCommand(collection);
            Command removeById = new RemoveByIdCommand(collection);
            Command removeGreater = new RemoveGreaterCommand(collection);
            Command removeLower = new RemoveLowerCommand(collection);
            Command save = new SaveCommand(jsonManager, collection);
            Command show = new ShowCommand(collection);
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
            commands.put(update.getName(), update);
        }
    }

    public void seekCommand(InputScriptReader reader, String line) throws IncorrectInputException {
        if (line.equals("")) {
        } else {
            String[] input = line.split(" ");
            if (input.length == 0) {
            } else if (input.length > 2)
                throw new IncorrectInputException("Неизвестная команда. Введите команду help, чтобы посмотреть информацию о коммандах");
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
                    throw new IncorrectInputException("Неизвестная команда. Введите команду help, чтобы посмотреть информацию о коммандах");
                }
            }
        }
    }

    public Collection<Command> getCommands() {
        return commands.values();
    }

    public Set<String> getCommandNames() {
        return commands.keySet();
    }

    public void addNewCommand(ArgsCommand newCommand) {
        commands.put(newCommand.getName(), newCommand);
    }
}