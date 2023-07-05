package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.MyScanner;
import settings.Settings;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class CommandProcessor {
    private static final Logger LOGGER = LogManager.getLogger(CommandProcessor.class);
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

    public String execute(String commandName) {
        return commands.get(commandName).execute();
    }

    public String exFromScript(MyScanner script, String line) {
        return commands.get(line.split(" ")[0]).exFromScript(script, line);
    }
}
