package commands;

import GUI.MyConsole;
import settings.Settings;

import java.util.Set;

public class ClearCommand extends Command {
    ClearCommand(Settings settings) {
        super(settings, "clear");
    }

    @Override
    public void execute(MyConsole output) {

    }
}
