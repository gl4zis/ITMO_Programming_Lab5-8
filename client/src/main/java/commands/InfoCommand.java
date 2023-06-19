package commands;

import GUI.MyConsole;
import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

public class InfoCommand extends Command {
    InfoCommand(Settings settings) {
        super(settings, "info");
    }

    @Override
    public void execute(MyConsole output) {
        output.addText("-----INFO-----");
        String reply = settings.tryConnect(new Request(CommandType.INFO, null, null, settings.getUser()));
        output.addText(Objects.requireNonNullElse(reply, "No connection ("));
    }

    @Override
    public void exFromScript(MyConsole output, MyScanner script, String line) {
        execute(output);
    }
}
