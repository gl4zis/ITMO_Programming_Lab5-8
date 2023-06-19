package commands;

import GUI.MyConsole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.MyScanner;
import settings.Settings;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class CommandProcessor {
    private static final Logger LOGGER = LogManager.getLogger();
    private final HashMap<String, Command> commands;
    private final Settings settings;

    public CommandProcessor(Settings settings) {
        this.settings = settings;
        commands = new HashMap<>();
        for (CommandType commandType : CommandType.values()) {
            if (commandType.haveSelfButton())
                addCommands(commandType.getName());
        }
        commands.put("execute_script", new ExecuteScriptCommand(settings));
    }

    @SuppressWarnings("unchecked")
    private void addCommands(String name) {
        String className = parseCommandName(name);
        try {
            Class<? extends Command> commandClass = (Class<? extends Command>) Class.forName(className);
            Command command = commandClass.getDeclaredConstructor(Settings.class).newInstance(settings);
            commands.put(name, command);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            LOGGER.error(e.getMessage());
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

    public void execute(String commandName, MyConsole output) {
        commands.get(commandName).execute(output);
    }

    public void exFromScript(MyConsole output, MyScanner script, String line) {
        commands.get(line.split(" ")[0]).exFromScript(output, script, line);
    }
}
