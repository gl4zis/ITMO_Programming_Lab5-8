package commands;

import GUI.MyConsole;
import dragons.Dragon;
import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

public class RemoveGreaterCommand extends Command {
    RemoveGreaterCommand(Settings settings) {
        super(settings, "remove_greater");
    }

    @Override
    public void execute(MyConsole output) {
        Dragon dragon = readDragon();
        if (dragon != null) {
            output.addText("-----REMOVE_GREATER-----");
            String reply = settings.tryConnect(new Request(CommandType.REMOVE_GREATER, null, dragon, settings.getUser()));
            output.addText(Objects.requireNonNullElse(reply, "No connection ("));
        }
    }

    @Override
    public void exFromScript(MyConsole output, MyScanner script, String line) {
        output.addText("-----REMOVE_GREATER-----");
        Dragon dragon = script.readDragon(settings.getUser());
        if (dragon != null) {
            String reply = settings.tryConnect(new Request(CommandType.REMOVE_GREATER, null, dragon, settings.getUser()));
            output.addText(Objects.requireNonNullElse(reply, "No connection ("));
        } else
            output.addText("Incorrect script");
    }
}
