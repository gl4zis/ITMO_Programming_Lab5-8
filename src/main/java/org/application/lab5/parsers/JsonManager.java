package org.application.lab5.parsers;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;

/**
 * Class, what reads data from JSON file and writes data into it
 */

public class JsonManager {
    private File file;

    /** Constructor, that creates object, trying to get file path from environment variable
     * @param env environment variable, that have file path
     */
    public JsonManager(String env) {
        try {
            String filePath = System.getenv(env);
            file = new File(filePath);
        } catch (NullPointerException e) {
            System.out.println("Переменной '" + env + "' не существует");
            System.out.println("Введите путь к JSON файлу коллекции");
            initJson();
        }
    }

    /** Method, what gets file path from console line, if something wrong with environment var
     */
    private void initJson() {
        while (true) {
            try {
                String filePath = InputConsoleReader.readNextLine();
                file = new File(filePath);
                break;
            } catch (NullPointerException e) {
                System.out.println("Введена пустая строка");
            }
        }
    }

    /** Gets new InputStreamReader.
     * Reads new file path from console if something wrong with file
     * @return reader
     */
    private Reader getNewReader() {
        while (true) {
            try {
                FileInputStream in = new FileInputStream(file);
                return new InputStreamReader(in);
            } catch (FileNotFoundException | SecurityException e) {
                System.out.println("Файла не существует или нет доступа к файлу");
                System.out.println("Введите путь к JSON файлу коллекции");
                initJson();
            }
        }
    }

    /** Reads JSON file and convert it to the JSONObject.
     * If file is incorrect json or file is empty, create new empty JSONObject.
     * If something wrong with file, closes the app
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
                System.out.println("Некорректный файл");
                return new JSONObject();
            } catch (IOException e) {
                System.out.println("Что-то пошло не так =(");
                initJson();
            }
        }
    }

    /** Writes JSONObject in JSON file
     * @param json JSONObject, that saves in the JSON file
     */
    public void writeJSON(JSONObject json) {
        try (FileOutputStream writer = new FileOutputStream(file)) {
            String output = jsonFormating(json.toJSONString());
            writer.write(output.getBytes());
        } catch (IOException | SecurityException e) {
            System.out.println("Нет доступа к файлу");
        }
    }

    /** Formats output string, what writes in file to have beautiful JSON =)
     * @param json string, that will be formatting, expects one-line JSON
     * @return output
     */
    private String jsonFormating(String json) {
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
