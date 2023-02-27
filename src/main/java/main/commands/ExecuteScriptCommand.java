package main.commands;

import main.InputReader;
import main.exceptions.IncorrectInputException;

import java.io.*;

public class ExecuteScriptCommand extends ArgsCommand {
    private static ExecuteScriptCommand executeScriptCommand;

    private ExecuteScriptCommand(String name) {
        super(name);
    }

    public static ExecuteScriptCommand getInstance() {
        if (executeScriptCommand == null) executeScriptCommand = new ExecuteScriptCommand("execute_script");
        return executeScriptCommand;
    }

    @Override
    public void scriptExecute(Reader reader, String arg) {
        execute(arg);
    }

    @Override
    public void execute(String filePath) {
        File file;
        FileInputStream in;
        Reader reader = null;
        try {
            file = new File(filePath);
            in = new FileInputStream(file);
            reader = new InputStreamReader(in);
        } catch (FileNotFoundException | SecurityException | NullPointerException e) {
            System.out.println(e.getMessage());
            System.out.println("Неправильно указан путь или нет доступа к файлу");
        }

        try {
            String line = InputReader.readNextFileLine(reader);
            while (line != null) {
                if (line.equals("execute_script " + filePath))
                    System.out.println("Попытка рекурсивного вызова файла!!");
                else {
                    System.out.println("Выполнение: " + line);
                    try {
                        Command.seekScriptCommand(reader, line);
                    } catch (IncorrectInputException e2) {
                        System.out.println(e2.getMessage());
                    }
                }
                line = InputReader.readNextFileLine(reader);
            }
        } catch (IOException e) {
            System.out.println("Что-то случилось с файликом =(");
        } catch (NullPointerException e) {
            System.out.println("Выполнение скрипта закончено");
        }
    }
}