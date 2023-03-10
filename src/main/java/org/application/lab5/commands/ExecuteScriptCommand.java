package org.application.lab5.commands;

import org.application.lab5.Main;
import org.application.lab5.exceptions.IncorrectDataException;
import org.application.lab5.parsers.InputScriptReader;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

public class ExecuteScriptCommand extends ArgsCommand {

    private static final Set<String> files = new HashSet<>();

    ExecuteScriptCommand() {
        super("execute_script", "execute_script file_name : считать и исполнить скрипт из указанного файла");
    }

    @Override
    public void execute(InputScriptReader reader, String filePath) {
        if (!files.contains(filePath)) {
            files.add(filePath);
            try {
                InputScriptReader newReader = new InputScriptReader(filePath);
                String line = newReader.findNextCommand();
                while (line != null) {
                    System.out.print("Выполнение: ");
                    System.out.println(line);
                    try {
                        Main.COMMAND_MANAGER.seekCommand(newReader, line);
                    } catch (NullPointerException | IncorrectDataException e) {
                        System.out.println("Ошибка! Команда не выполнена");
                    }
                    line = newReader.findNextCommand();
                }
                System.out.println("Выполнение скрипта закончено");
            } catch (FileNotFoundException | SecurityException e) {
                System.out.println("Файл не найден или нет доступа к файлу");
            }
        } else System.out.println("Попытка вызова рекурсии! Команда не выполнена");
    }
}