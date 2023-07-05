package commands;

import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

public class ClearCommand extends Command {
    ClearCommand(Settings settings) {
        super(settings, "clear");
    }

    @Override
    public String execute() {
        StringBuilder output = new StringBuilder();
        output.append("-----CLEAR-----\n");
        String reply = settings.tryConnect(new Request(CommandType.CLEAR, null, null, settings.getUser()));
        output.append(Objects.requireNonNullElse(reply, "No connection ("));
        return output.toString();
    }

    @Override
    public String exFromScript(MyScanner script, String line) {
        return execute();
    }
}
