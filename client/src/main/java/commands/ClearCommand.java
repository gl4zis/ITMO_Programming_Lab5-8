package commands;

import GUI.MyConsole;
import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

public class ClearCommand extends Command {
    ClearCommand(Settings settings) {
        super(settings, "clear");
    }

    @Override
    public void execute(MyConsole output) {
        output.addText("-----CLEAR-----");
        String reply = settings.tryConnect(new Request(CommandType.CLEAR, null, null, settings.getUser()));
        output.addText(Objects.requireNonNullElse(reply, "No connection ("));
    }

    @Override
    public void exFromScript(MyConsole output, MyScanner script, String line) {
        execute(output);
    }
}
