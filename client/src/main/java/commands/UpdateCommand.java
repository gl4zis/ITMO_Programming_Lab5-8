package commands;

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
    public String execute() {
        StringBuilder output = new StringBuilder();
        Integer id = (Integer) readNumber("ID", Integer.class);
        if (id != null) {
            output.append("-----UPDATE-----\n");
            String find = settings.tryConnect(new Request(CommandType.FIND, id, null, settings.getUser()));
            if (find == null)
                output.append("No connection (");
            else if (find.startsWith("No such"))
                output.append(find);
            else {
                Dragon dragon = readDragon();
                if (dragon != null) {
                    String update = settings.tryConnect(new Request(CommandType.UPDATE, id, dragon, settings.getUser()));
                    output.append(Objects.requireNonNullElse(update, "No connection ("));
                }
            }
        }
        return output.toString();
    }

    @Override
    public String exFromScript(MyScanner script, String line) {
        StringBuilder output = new StringBuilder();
        output.append("-----UPDATE-----\n");
        try {
            int id = Integer.parseInt(line.split(" ")[1]);
            String find = settings.tryConnect(new Request(CommandType.FIND, id, null, settings.getUser()));
            if (find == null)
                output.append("No connection (");
            else if (find.startsWith("No such"))
                output.append(find);
            else {
                Dragon dragon = script.readDragon(settings.getUser());
                if (dragon != null) {
                    String update = settings.tryConnect(new Request(CommandType.UPDATE, id, dragon, settings.getUser()));
                    output.append(Objects.requireNonNullElse(update, "No connection ("));
                } else
                    output.append("Incorrect script");
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            output.append("Incorrect script");
        }
        return output.toString();
    }
}
