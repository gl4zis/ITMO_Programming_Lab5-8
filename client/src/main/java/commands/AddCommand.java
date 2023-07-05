package commands;

import dragons.Dragon;
import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

public class AddCommand extends Command {
    AddCommand(Settings settings) {
        super(settings, "add");
    }

    @Override
    public String execute() {
        Dragon dragon = readDragon();
        StringBuilder output = new StringBuilder();
        if (dragon != null) {
            output.append("-----ADD-----\n");
            String reply = settings.tryConnect(new Request(CommandType.ADD, null, dragon, settings.getUser()));
            output.append(Objects.requireNonNullElse(reply, "No connection ("));
        }
        return output.toString();
    }

    @Override
    public String exFromScript(MyScanner script, String line) {
        StringBuilder output = new StringBuilder();
        output.append("-----ADD-----\n");
        Dragon dragon = script.readDragon(settings.getUser());
        if (dragon != null) {
            String reply = settings.tryConnect(new Request(CommandType.ADD, null, dragon, settings.getUser()));
            output.append(Objects.requireNonNullElse(reply, "No connection ("));
        } else
            output.append("Incorrect script");
        return output.toString();
    }
}
