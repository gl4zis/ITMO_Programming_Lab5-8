package commands;

import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

public class MinByAgeCommand extends Command {
    MinByAgeCommand(Settings settings) {
        super(settings, "min_by_age");
    }

    @Override
    public String execute() {
        StringBuilder output = new StringBuilder();
        output.append("-----MIN_BY_AGE-----\n");
        String reply = settings.tryConnect(new Request(CommandType.MIN_BY_AGE, null, null, settings.getUser()));
        output.append(Objects.requireNonNullElse(reply, "No connection ("));
        return output.toString();
    }

    @Override
    public String exFromScript(MyScanner script, String line) {
        return execute();
    }
}
