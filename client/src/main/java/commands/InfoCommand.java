package commands;

import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

public class InfoCommand extends Command {
    InfoCommand(Settings settings) {
        super(settings, "info");
    }

    @Override
    public String execute() {
        StringBuilder output = new StringBuilder();
        output.append("-----INFO-----\n");
        String reply = settings.tryConnect(new Request(CommandType.INFO, null, null, settings.getUser()));
        output.append(Objects.requireNonNullElse(reply, "No connection ("));
        return output.toString();
    }

    @Override
    public String exFromScript(MyScanner script, String line) {
        return execute();
    }
}
