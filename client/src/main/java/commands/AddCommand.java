package commands;

import GUI.MyConsole;
import dragons.Dragon;
import exceptions.UnavailableServerException;
import network.Request;
import settings.Settings;

import java.util.Set;

public class AddCommand extends Command {
    AddCommand(Settings settings) {
        super(settings, "add");
    }

    @Override
    public void execute(MyConsole output) {
        Dragon dragon = readDragon();
        if (dragon != null) {
            String reply = settings.tryConnect(new Request(CommandType.ADD, null, dragon, settings.getUser()));
            if (reply == null)
                output.addText("No connection (");
            else
                output.addText("-----ADD-----\n" + reply);
        }
    }
}
