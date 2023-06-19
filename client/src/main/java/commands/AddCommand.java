package commands;

import GUI.MyConsole;
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
    public void execute(MyConsole output) {
        Dragon dragon = readDragon();
        if (dragon != null) {
            output.addText("-----ADD-----");
            String reply = settings.tryConnect(new Request(CommandType.ADD, null, dragon, settings.getUser()));
            output.addText(Objects.requireNonNullElse(reply, "No connection ("));
        }
    }

    @Override
    public void exFromScript(MyConsole output, MyScanner script, String line) {
        output.addText("-----ADD-----");
        Dragon dragon = script.readDragon(settings.getUser());
        if (dragon != null) {
            String reply = settings.tryConnect(new Request(CommandType.ADD, null, dragon, settings.getUser()));
            output.addText(Objects.requireNonNullElse(reply, "No connection ("));
        } else
            output.addText("Incorrect script");
    }
}
