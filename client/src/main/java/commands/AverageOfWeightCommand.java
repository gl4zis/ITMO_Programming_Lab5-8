package commands;

import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

public class AverageOfWeightCommand extends Command {
    AverageOfWeightCommand(Settings settings) {
        super(settings, "average_of_weight");
    }

    @Override
    public String execute() {
        StringBuilder output = new StringBuilder();
        output.append("-----AVERAGE_OF_WEIGHT-----\n");
        String reply = settings.tryConnect(new Request(CommandType.AVERAGE_OF_WEIGHT, null, null, settings.getUser()));
        output.append(Objects.requireNonNullElse(reply, "No connection ("));
        return output.toString();
    }

    @Override
    public String exFromScript(MyScanner script, String line) {
        return execute();
    }
}
