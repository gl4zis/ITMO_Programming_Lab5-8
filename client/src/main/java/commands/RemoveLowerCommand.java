package commands;

import dragons.Dragon;
import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

public class RemoveLowerCommand extends Command {
    RemoveLowerCommand(Settings settings) {
        super(settings, "remove_lower");
    }

    @Override
    public String execute() {
        StringBuilder output = new StringBuilder();
        Dragon dragon = readDragon();
        if (dragon != null) {
            output.append("-----REMOVE_LOWER-----\n");
            String reply = settings.tryConnect(new Request(CommandType.REMOVE_LOWER, null, dragon, settings.getUser()));
            output.append(Objects.requireNonNullElse(reply, "No connection ("));
        }
        return output.toString();
    }

    @Override
    public String exFromScript(MyScanner script, String line) {
        StringBuilder output = new StringBuilder();
        output.append("-----REMOVE_LOWER-----\n");
        Dragon dragon = script.readDragon(settings.getUser());
        if (dragon != null) {
            String reply = settings.tryConnect(new Request(CommandType.REMOVE_LOWER, null, dragon, settings.getUser()));
            output.append(Objects.requireNonNullElse(reply, "No connection ("));
        } else
            output.append("Incorrect script");
        return output.toString();
    }
}
