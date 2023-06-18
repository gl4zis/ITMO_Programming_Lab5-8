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
            try {
                String reply = settings.getConnection().sendReqGetResp(new Request(CommandType.ADD, null, dragon, settings.getUser()));
                output.addText("-----ADD-----\n  " + reply);
            } catch (UnavailableServerException e) {
                e.printStackTrace();
                settings.getConnection().connected = false;
                output.addText("No connection");
            }
        }
    }
}
