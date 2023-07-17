package commands;

import dragons.Dragon;
import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

public class RemoveGreaterCommand extends Command {
    RemoveGreaterCommand(Settings settings) {
        super(settings, "remove_greater");
    }

    @Override
    public String execute() {
        StringBuilder output = new StringBuilder();
        Dragon dragon = readDragon();
        if (dragon != null) {
            output.append("-----REMOVE_GREATER-----\n");
            String reply = settings.getResponse(new Request(CommandType.REMOVE_GREATER, null, dragon, settings.getUser()));
            output.append(Objects.requireNonNullElse(reply, "No connection ("));
        }
        return output.toString();
    }

    @Override
    public String exFromScript(MyScanner script, String line) {
        StringBuilder output = new StringBuilder();
        output.append("-----REMOVE_GREATER-----\n");
        Dragon dragon = script.readDragon(settings.getUser());
        if (dragon != null) {
            String reply = settings.getResponse(new Request(CommandType.REMOVE_GREATER, null, dragon, settings.getUser()));
            output.append(Objects.requireNonNullElse(reply, "No connection ("));
        } else
            output.append("Incorrect script");
        return output.toString();
    }
}
