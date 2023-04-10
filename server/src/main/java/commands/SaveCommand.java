package commands;

import collection.DragonCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import parsers.JsonManager;

/**
 * Non-argument command "save". Saves collection to the JSON file
 */
public class SaveCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(SaveCommand.class);
    private final JsonManager jsonManager;
    private final DragonCollection collection;

    /**
     * Constructor, sets collection and json managers, that the command works with, name and description of command
     */
    public SaveCommand(JsonManager jsonManager, DragonCollection collection) {
        super("save", "save : сохранить коллекцию в файл");
        this.collection = collection;
        this.jsonManager = jsonManager;
    }

    /**
     * Saves collection in JSON file.
     * If save failed, outputs error message
     */
    @Override
    public String execute() {
        JSONObject jsonCOll = collection.toJson();
        jsonManager.writeJSON(jsonCOll);
        collection.saved = true;
        LOGGER.debug("Save command was successfully executed");
        return "Save command was successfully executed";
    }
}