package commands;

import GUI.MyConsole;
import dragons.Dragon;
import network.Request;
import parsers.MyScanner;
import settings.Settings;

import java.util.Objects;

/**
 * Command 'update'
 */
public class UpdateCommand extends Command {
    UpdateCommand(Settings settings) {
        super(settings, "update");
    }

    /**
     * Sends 2 requests, 'find id' and then, if dragon exists 'update id'
     */
    @Override
    public void execute(MyConsole output) {
        Integer id = (Integer) readNumber("ID", Integer.class);
        if (id != null) {
            output.addText("-----UPDATE-----");
            String find = settings.tryConnect(new Request(CommandType.FIND, id, null, settings.getUser()));
            if (find == null)
                output.addText("No connection (");
            else if (find.startsWith("No such"))
                output.addText(find);
            else {
                Dragon dragon = readDragon();
                if (dragon != null) {
                    String update = settings.tryConnect(new Request(CommandType.UPDATE, id, dragon, settings.getUser()));
                    output.addText(Objects.requireNonNullElse(update, "No connection ("));
                }
            }
        }
    }

    @Override
    public void exFromScript(MyConsole output, MyScanner script, String line) {
        output.addText("-----UPDATE-----");
        try {
            int id = Integer.parseInt(line.split(" ")[1]);
            String find = settings.tryConnect(new Request(CommandType.FIND, id, null, settings.getUser()));
            if (find == null)
                output.addText("No connection (");
            else if (find.startsWith("No such"))
                output.addText(find);
            else {
                Dragon dragon = script.readDragon(settings.getUser());
                if (dragon != null) {
                    String update = settings.tryConnect(new Request(CommandType.UPDATE, id, dragon, settings.getUser()));
                    output.addText(Objects.requireNonNullElse(update, "No connection ("));
                } else
                    output.addText("Incorrect script");
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            output.addText("Incorrect script");
        }
    }
}
