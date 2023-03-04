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
    public void execute(String filePath) {
    }
}