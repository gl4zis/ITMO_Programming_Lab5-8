package main;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Dictionary;
import java.util.List;


public class JsonManager {

    private final String env;
    String filePath;
    File file;

    public JsonManager(String env) {
        this.env = env;
        initJson();
    }

    private void initJson() {
        while (true) {
            try {
                filePath = System.getenv(env);
                file = new File(filePath);
                break;
            } catch (NullPointerException e) {
                System.out.println("Такой переменной не существует");
                System.out.println("Введите путь к JSON файлу коллекции");
                filePath = InputConsoleReader.readNextLine();
            }
        }
    }

    private Reader getNewReader(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            return new InputStreamReader(in);
        } catch (FileNotFoundException | SecurityException e) {
            System.out.println("Нет доступа к файлу или файл не существует");
            return null;
        }
    }

    public JSONObject readJSON() {
        Reader reader = getNewReader(file);
        try {
            return (JSONObject) new JSONParser().parse(reader);
        } catch (ParseException e) {
            System.out.println("Некорректный файл");
            return new JSONObject();
        } catch (IOException | NullPointerException e) {
            System.out.println("Что-то случилось с файликом =(\n" +
                    "Попробуйте перезапустить программу");
            return null;
        }
    }

    public void writeJSON(JSONObject json) throws IOException {
        try (FileOutputStream writer = new FileOutputStream(file)) {
            String output = jsonFormating(json.toJSONString());
            writer.write(output.getBytes());
        } catch (FileNotFoundException | SecurityException e) {
            System.out.println("Нет доступа к файлу");
        }
    }

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
                output.append("\t".repeat(Math.max(0, tabs)));
            }
            output.append(sym);
            if (sym.equals("\"")) inStr = !inStr;
            if (openSym.contains(sym) || sym.equals(",")) {
                output.append("\n");
                if (openSym.contains(sym)) tabs++;
                output.append("\t".repeat(Math.max(0, tabs)));
            } else if (sym.equals(":") && !inStr) {
                output.append(" ");
            }
        }
        return output.toString();
    }
}
