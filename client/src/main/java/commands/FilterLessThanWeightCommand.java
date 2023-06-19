package commands;

import GUI.MyConsole;
import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

public class FilterLessThanWeightCommand extends Command {
    FilterLessThanWeightCommand(Settings settings) {
        super(settings, "filter_less_than_weight");
    }

    @Override
    public void execute(MyConsole output) {
        Long weight = (Long) readNumber("Weight", Long.class);
        if (weight != null) {
            output.addText("-----FILTER_LESS_THAN_WEIGHT-----");
            String reply = settings.tryConnect(new Request(CommandType.FILTER_LESS_THAN_WEIGHT, weight, null, settings.getUser()));
            output.addText(Objects.requireNonNullElse(reply, "No connection ("));
        }
    }

    @Override
    public void exFromScript(MyConsole output, MyScanner script, String line) {
        output.addText("-----FILTER_LESS_THAN_WEIGHT-----");
        try {
            Long weight = Long.parseLong(line.split(" ")[1]);
            String reply = settings.tryConnect(new Request(CommandType.FILTER_LESS_THAN_WEIGHT, weight, null, settings.getUser()));
            output.addText(Objects.requireNonNullElse(reply, "No connection ("));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            output.addText("Incorrect script");
        }
    }
}
