package commands;

import GUI.MyConsole;
import settings.Settings;

public class InfoCommand extends Command {
    InfoCommand(Settings settings) {
        super(settings, "info");
    }

    @Override
    public void execute(MyConsole output) {

    }
}
