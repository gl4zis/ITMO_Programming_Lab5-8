package commands;

import GUI.MyConsole;
import dragons.Dragon;
import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

public class RemoveLowerCommand extends Command {
    RemoveLowerCommand(Settings settings) {
        super(settings, "remove_lower");
    }

    @Override
    public void execute(MyConsole output) {
        Dragon dragon = readDragon();
        if (dragon != null) {
            output.addText("-----REMOVE_LOWER-----");
            String reply = settings.tryConnect(new Request(CommandType.REMOVE_LOWER, null, dragon, settings.getUser()));
            output.addText(Objects.requireNonNullElse(reply, "No connection ("));
        }
    }

    @Override
    public void exFromScript(MyConsole output, MyScanner script, String line) {
        output.addText("-----REMOVE_LOWER-----");
        Dragon dragon = script.readDragon(settings.getUser());
        if (dragon != null) {
            String reply = settings.tryConnect(new Request(CommandType.REMOVE_LOWER, null, dragon, settings.getUser()));
            output.addText(Objects.requireNonNullElse(reply, "No connection ("));
        } else
            output.addText("Incorrect script");
    }
}
