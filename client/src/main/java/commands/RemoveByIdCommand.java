package commands;

import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

public class RemoveByIdCommand extends Command {
    RemoveByIdCommand(Settings settings) {
        super(settings, "remove_by_id");
    }

    @Override
    public String execute() {
        StringBuilder output = new StringBuilder();
        Integer id = (Integer) readNumber("ID", Integer.class);
        if (id != null) {
            output.append("-----REMOVE_BY_ID-----\n");
            String reply = settings.getResponse(new Request(CommandType.REMOVE_BY_ID, id, null, settings.getUser()));
            output.append(Objects.requireNonNullElse(reply, "No connection ("));
        }
        return output.toString();
    }

    @Override
    public String exFromScript(MyScanner script, String line) {
        StringBuilder output = new StringBuilder();
        output.append("-----REMOVE_BY_ID-----\n");
        try {
            int id = Integer.parseInt(line.split(" ")[1]);
            String reply = settings.getResponse(new Request(CommandType.REMOVE_BY_ID, id, null, settings.getUser()));
            output.append(Objects.requireNonNullElse(reply, "No connection ("));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            output.append("Incorrect script");
        }
        return output.toString();
    }
}
