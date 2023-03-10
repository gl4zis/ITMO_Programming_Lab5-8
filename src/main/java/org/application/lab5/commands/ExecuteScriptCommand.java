package org.application.lab5.commands;

import org.application.lab5.Main;
import org.application.lab5.parsers.InputScriptReader;

import java.io.FileNotFoundException;

public class ExecuteScriptCommand extends ArgsCommand {

    ExecuteScriptCommand() {
        super("execute_script", "execute_script file_name : считать и исполнить скрипт из указанного файла");
    }

    @Override
    public void execute(InputScriptReader reader, String filePath) {
        try {
            InputScriptReader newReader = new InputScriptReader(filePath);
            while (newReader.execution) {
                String line = newReader.readNextLine();
                System.out.print("Выполнение: ");
                System.out.println(line);
                Main.COMMAND_MANAGER.seekCommand(newReader, line);
            }
        } catch (FileNotFoundException | SecurityException e) {
            System.out.println("Файл не найден или нет доступа к файлу");
        }
    }
}