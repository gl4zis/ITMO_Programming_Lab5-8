package commands;

import GUI.MyConsole;
import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

public class RemoveByIdCommand extends Command {
    RemoveByIdCommand(Settings settings) {
        super(settings, "remove_by_id");
    }

    @Override
    public void execute(MyConsole output) {
        Integer id = (Integer) readNumber("ID", Integer.class);
        if (id != null) {
            output.addText("-----REMOVE_BY_ID-----");
            String reply = settings.tryConnect(new Request(CommandType.REMOVE_BY_ID, id, null, settings.getUser()));
            output.addText(Objects.requireNonNullElse(reply, "No connection ("));
        }
    }

    @Override
    public void exFromScript(MyConsole output, MyScanner script, String line) {
        output.addText("-----REMOVE_BY_ID-----");
        try {
            int id = Integer.parseInt(line.split(" ")[1]);
            String reply = settings.tryConnect(new Request(CommandType.REMOVE_BY_ID, id, null, settings.getUser()));
            output.addText(Objects.requireNonNullElse(reply, "No connection ("));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            output.addText("Incorrect script");
        }
    }
}
