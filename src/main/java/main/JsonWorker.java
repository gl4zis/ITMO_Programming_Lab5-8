package main;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;


public abstract class JsonWorker {
    private static File file;
    private static InputStream in;
    private static java.io.Reader reader;

    static {
        String env = "config";
        do {
            try {
                file = new File(System.getenv(env));
                in = new FileInputStream(file);
                reader = new InputStreamReader(in);
                break;
            } catch (NullPointerException e) {
                System.out.println("Такой переменной не существует");
                System.out.println("Введите имя переменной окружения, в которой записан путь к JSON файлу коллекции: ");
                env = InputReader.readNextConsoleLine();
            } catch (FileNotFoundException | SecurityException e) {
                System.out.println("Неправильно указан путь или нет доступа к файлу");
                System.out.println("Введите имя переменной окружения, в которой записан путь к JSON файлу коллекции: ");
                env = InputReader.readNextConsoleLine();
            }
        } while (true);
    }

    public static JSONObject readJSON() {
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(reader);
            return json;
        } catch (ParseException e) {
            System.out.println("Некорректный файл");
            return new JSONObject();
        } catch (IOException e) {
            System.out.println("Что-то случилось с файликом =(\n" +
                    "Попробуйте перезапустить программу");
            return null;
        }
    }

    public static void writeJSON(JSONObject json) throws IOException {
        FileOutputStream writer = new FileOutputStream(file);
        String output = jsonFormat(json.toJSONString());
        writer.write(output.getBytes());
    }

    private static String jsonFormat(String json) {
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
