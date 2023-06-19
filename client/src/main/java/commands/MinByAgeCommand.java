package commands;

import GUI.MyConsole;
import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

public class MinByAgeCommand extends Command {
    MinByAgeCommand(Settings settings) {
        super(settings, "min_by_age");
    }

    @Override
    public void execute(MyConsole output) {
        output.addText("-----MIN_BY_AGE-----");
        String reply = settings.tryConnect(new Request(CommandType.MIN_BY_AGE, null, null, settings.getUser()));
        output.addText(Objects.requireNonNullElse(reply, "No connection ("));
    }

    @Override
    public void exFromScript(MyConsole output, MyScanner script, String line) {
        execute(output);
    }
}
