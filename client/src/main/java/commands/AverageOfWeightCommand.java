package commands;

import GUI.MyConsole;
import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

public class AverageOfWeightCommand extends Command {
    AverageOfWeightCommand(Settings settings) {
        super(settings, "average_of_weight");
    }

    @Override
    public void execute(MyConsole output) {
        output.addText("-----AVERAGE_OF_WEIGHT-----");
        String reply = settings.tryConnect(new Request(CommandType.AVERAGE_OF_WEIGHT, null, null, settings.getUser()));
        output.addText(Objects.requireNonNullElse(reply, "No connection ("));
    }

    @Override
    public void exFromScript(MyConsole output, MyScanner script, String line) {
        execute(output);
    }
}
