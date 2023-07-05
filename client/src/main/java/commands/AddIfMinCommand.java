package commands;

import dragons.Dragon;
import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

public class AddIfMinCommand extends Command {
    AddIfMinCommand(Settings settings) {
        super(settings, "add_if_min");
    }

    @Override
    public String execute() {
        StringBuilder output = new StringBuilder();
        Dragon dragon = readDragon();
        if (dragon != null) {
            output.append("-----ADD_IF_MIN-----\n");
            String reply = settings.tryConnect(new Request(CommandType.ADD_IF_MIN, null, dragon, settings.getUser()));
            output.append(Objects.requireNonNullElse(reply, "No connection ("));
        }
        return output.toString();
    }

    @Override
    public String exFromScript(MyScanner script, String line) {
        StringBuilder output = new StringBuilder();
        output.append("-----ADD_IF_MIN-----\n");
        Dragon dragon = script.readDragon(settings.getUser());
        if (dragon != null) {
            String reply = settings.tryConnect(new Request(CommandType.ADD_IF_MIN, null, dragon, settings.getUser()));
            output.append(Objects.requireNonNullElse(reply, "No connection ("));
        } else
            output.append("Incorrect script");
        return output.toString();
    }
}