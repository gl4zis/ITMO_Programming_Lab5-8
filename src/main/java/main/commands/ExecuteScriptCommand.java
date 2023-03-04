package main.commands;

import main.InputReader;
import main.exceptions.IncorrectInputException;

import java.io.*;

public class ExecuteScriptCommand extends ArgsCommand {
    private ExecuteScriptCommand(String name) {
        super(name);
    }

    private static class CommandHolder {
        public static final Command INSTANCE = new ExecuteScriptCommand("execute_script");
    }

    public static Command getInstance() {
        return CommandHolder.INSTANCE;
    }

    @Override
    public void execute(String filePath) {
        //Реализовать!!!
    }
}