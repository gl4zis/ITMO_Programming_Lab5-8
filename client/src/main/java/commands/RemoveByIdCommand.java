package commands;

import GUI.MyConsole;
import settings.Settings;

public class RemoveByIdCommand extends Command {
    RemoveByIdCommand(Settings settings) {
        super(settings, "remove_by_id");
    }

    @Override
    public void execute(MyConsole output) {

    }
}
