package commands;

import GUI.DragonDialog;
import GUI.MyConsole;
import GUI.NumberDialog;
import dragons.Dragon;
import parsers.MyScanner;
import settings.Settings;

/**
 * Abstract class of commands, which need to be processed on client
 */
public abstract class Command {

    protected final Settings settings;
    private final String name;

    protected Command(Settings settings, String name) {
        this.settings = settings;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void execute(MyConsole output);

    public abstract void exFromScript(MyConsole output, MyScanner script, String line);

    protected Dragon readDragon() {
        return new DragonDialog(settings.getMainWindow()).getDragon();
    }

    protected Number readNumber(String name, Class<? extends Number> type) {
        return new NumberDialog(settings.getMainWindow(), name, type).getVar();
    }
}
