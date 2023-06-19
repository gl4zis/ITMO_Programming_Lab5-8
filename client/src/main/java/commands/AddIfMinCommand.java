package commands;

import GUI.MyConsole;
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
    public void execute(MyConsole output) {
        Dragon dragon = readDragon();
        if (dragon != null) {
            output.addText("-----ADD_IF_MIN-----");
            String reply = settings.tryConnect(new Request(CommandType.ADD_IF_MIN, null, dragon, settings.getUser()));
            output.addText(Objects.requireNonNullElse(reply, "No connection ("));
        }
    }

    @Override
    public void exFromScript(MyConsole output, MyScanner script, String line) {
        output.addText("-----ADD_IF_MIN-----");
        Dragon dragon = script.readDragon(settings.getUser());
        if (dragon != null) {
            String reply = settings.tryConnect(new Request(CommandType.ADD_IF_MIN, null, dragon, settings.getUser()));
            output.addText(Objects.requireNonNullElse(reply, "No connection ("));
        } else
            output.addText("Incorrect script");
    }
}