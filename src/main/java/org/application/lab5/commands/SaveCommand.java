package org.application.lab5.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.application.lab5.collection.DragonCollection;
import org.application.lab5.collection.CollectionManager;
import org.application.lab5.parsers.InputScriptReader;
import org.application.lab5.parsers.JsonManager;

import java.io.IOException;

/**
 * Non-argument command "save". Saves collection to the JSON file
 */
public class SaveCommand extends NonArgsCommand {
    private static final Logger LOGGER = LogManager.getLogger(SaveCommand.class);
    private final JsonManager jsonManager;
    private final DragonCollection collection;

    /** Constructor, sets collection and json managers, that the command works with, name and description of command
     */
    SaveCommand(JsonManager jsonManager, DragonCollection collection) {
        super("save", "save : сохранить коллекцию в файл");
        this.collection = collection;
        this.jsonManager = jsonManager;
    }

    /** Saves collection in JSON file.
     * If save failed, outputs error message
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public void execute(InputScriptReader reader) {
        CollectionManager.saveCollection(jsonManager, collection);
        collection.saved = true;
        LOGGER.debug("Save command was successfully executed");
    }
}