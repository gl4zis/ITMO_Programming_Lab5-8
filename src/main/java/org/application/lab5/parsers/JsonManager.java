package org.application.lab5.parsers;


import org.application.lab5.parsers.InputConsoleReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;


public class JsonManager {
    private File file;

    public JsonManager(String env) {
        initJson(env);
    }

    private void initJson(String env) {
        try {
            String filePath = System.getenv(env);
            file = new File(filePath);
        } catch (NullPointerException e) {
            System.out.println("Переменной '" + env + "' не существует");
            System.out.println("Введите путь к JSON файлу коллекции");
            initJson();
        }
    }

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

    public JSONObject readJSON() {
        Reader reader = getNewReader();
        try {
            return (JSONObject) new JSONParser().parse(reader);
        } catch (ParseException e) {
            System.out.println("Некорректный файл");
            return new JSONObject();
        } catch (IOException | NullPointerException e) {
            System.out.println("Что-то случилось с файликом =(\n" +
                    "Попробуйте перезапустить программу");
            System.exit(0);
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
