package commands;

import GUI.MyConsole;
import client.ClientConnection;
import parsers.MyScanner;
import settings.Settings;

import javax.swing.*;
import java.util.Set;

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
}
