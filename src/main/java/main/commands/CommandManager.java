package main.commands;

import main.exceptions.IncorrectInputException;

import java.util.HashMap;
import java.util.Map;

public abstract class CommandManager {

    private static Map<String, Command> commands;

    private static synchronized void addStandartCommands() {
        if (commands == null) {
            commands = new HashMap<>();
            Command add = new AddCommand();
            Command addIfMin = new AddIfMinCommand();
            Command averageOfWeight = new AverageOfWeightCommand();
            Command clear = new ClearCommand();
            Command executeScript = new ExecuteScriptCommand();
            Command exit = new ExitCommand();
            Command filterLessThanWeight = new FilterLessThanWeightCommand();
            Command help = new HelpCommand();
            Command info = new InfoCommand();
            Command minByAge = new MinByAgeCommand();
            Command removeById = new RemoveByIdCommand();
            Command removeGreater = new RemoveGreaterCommand();
            Command removeLower = new RemoveLowerCommand();
            Command save = new SaveCommand();
            Command show = new ShowCommand();
            Command update = new UpdateCommand();
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

    public static void seekCommand(String line) throws IncorrectInputException {
        if (commands == null)
            addStandartCommands();
        String[] input = line.split(" ");
        if (input.length > 2 || input.length == 0)
            throw new IncorrectInputException("Неизвестная команда. Введите команду help, чтобы посмотреть информацию о коммандах");
        else {
            String command = input[0];
            if (commands.containsKey(command)) {
                try {
                    String arg = input[1];
                    commands.get(command).execute(arg);
                } catch (ArrayIndexOutOfBoundsException e) {
                    commands.get(command).execute();
                }
            } else {
                throw new IncorrectInputException("Неизвестная команда. Введите команду help, чтобы посмотреть информацию о коммандах");
            }
        }
    }

    public static synchronized void addNewCommand(ArgsCommand newCommand) {
        if (commands == null)
            addStandartCommands();
        commands.put(newCommand.getName(), newCommand);
    }
}