package commands;

import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

public class FilterLessThanWeightCommand extends Command {
    FilterLessThanWeightCommand(Settings settings) {
        super(settings, "filter_less_than_weight");
    }

    @Override
    public String execute() {
        StringBuilder output = new StringBuilder();
        Long weight = (Long) readNumber("Weight", Long.class);
        if (weight != null) {
            output.append("-----FILTER_LESS_THAN_WEIGHT-----\n");
            String reply = settings.tryConnect(new Request(CommandType.FILTER_LESS_THAN_WEIGHT, weight, null, settings.getUser()));
            output.append(Objects.requireNonNullElse(reply, "No connection ("));
        }
        return output.toString();
    }

    @Override
    public String exFromScript(MyScanner script, String line) {
        StringBuilder output = new StringBuilder();
        output.append("-----FILTER_LESS_THAN_WEIGHT-----\n");
        try {
            Long weight = Long.parseLong(line.split(" ")[1]);
            String reply = settings.tryConnect(new Request(CommandType.FILTER_LESS_THAN_WEIGHT, weight, null, settings.getUser()));
            output.append(Objects.requireNonNullElse(reply, "No connection ("));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            output.append("Incorrect script");
        }
        return output.toString();
    }
}
