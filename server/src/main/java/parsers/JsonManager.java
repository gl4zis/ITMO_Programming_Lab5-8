package parsers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;

/**
 * Class, what reads data from JSON file and writes data into it
 */

public class JsonManager {
    private static final Logger LOGGER = LogManager.getLogger(JsonManager.class);
    private File file;
    private static final MyScanner CONSOLE = new MyScanner(System.in);

    /**
     * Constructor, that creates object, trying to get file path from environment variable
     *
     * @param env environment variable, that have file path
     */
    public JsonManager(String env) {
        try {
            String filePath = System.getenv(env);
            filePath = StringModificator.filePathFormat(filePath);
            file = new File(filePath);
        } catch (NullPointerException e) {
            if (!env.equals(""))
                LOGGER.error("Environment '" + env + "' doesn't exist");
            System.out.println("Enter file path to the JSON file:");
            initJson();
        }
    }

    /**
     * Gets file path from console line, if something wrong with environment var
     */
    private void initJson() {
        while (true) {
            String filePath = StringModificator.filePathFormat(CONSOLE.nextLine());
            file = new File(filePath);
            if (!filePath.isEmpty())
                break;
        }
    }

    /**
     * Gets new InputStreamReader.
     * Reads new file path from console if something wrong with file
     *
     * @return reader
     */
    private Reader getNewReader() {
        while (true) {
            try {
                FileInputStream in = new FileInputStream(file);
                return new InputStreamReader(in);
            } catch (FileNotFoundException | SecurityException e) {
                LOGGER.error("File doesn't exist or there is no access to the file");
                System.out.println("Enter file path to the JSON file:");
                initJson();
            }
        }
    }

    /**
     * Reads JSON file and convert it to the JSONObject.
     * If file is incorrect json or file is empty, create new empty JSONObject.
     * If something wrong with file, closes the app
     *
     * @return JSONObject
     */
    public JSONObject readJSON() {
        while (true) {
            try {
                if (getNewReader().read() == -1) return new JSONObject();
                else {
                    return (JSONObject) new JSONParser().parse(getNewReader());
                }
            } catch (ParseException e) {
                LOGGER.debug("Incorrect format of file");
                return new JSONObject();
            } catch (IOException e) {
                LOGGER.error("Something went wrong with collection file");
                initJson();
            }
        }
    }

    /**
     * Writes JSONObject in JSON file
     *
     * @param json JSONObject, that saves in the JSON file
     */
    public void writeJSON(JSONObject json) {
        try (FileOutputStream writer = new FileOutputStream(file)) {
            String output = jsonFormatting(json.toJSONString());
            writer.write(output.getBytes());
            LOGGER.info("Collection saved successfully");
        } catch (IOException | SecurityException e) {
            LOGGER.warn("Error with access to the file");
        }
    }

    /**
     * Formats output string, what writes in file to have beautiful JSON =)
     *
     * @param json string, that will be formatting, expects one-line JSON
     * @return output
     */
    private String jsonFormatting(String json) {
        String[] input = json.split("");
        StringBuilder output = new StringBuilder();
        List<String> openSym = List.of(new String[]{"{", "["});
        List<String> closeSym = List.of(new String[]{"}", "]"});
        int tabs = 0;
        boolean inStr = false;
        for (String sym : input) {
            if (closeSym.contains(sym)) {
                output.append("\n");
                tabs--;
                output.append("    ".repeat(Math.max(0, tabs)));
            }
            output.append(sym);
            if (sym.equals("\"")) inStr = !inStr;
            if (openSym.contains(sym) || sym.equals(",")) {
                output.append("\n");
                if (openSym.contains(sym)) tabs++;
                output.append("    ".repeat(Math.max(0, tabs)));
            } else if (sym.equals(":") && !inStr) {
                output.append(" ");
            }
        }
        return output.toString();
    }
}
