package org.application.lab5.commands;


import org.application.lab5.parsers.InputScriptReader;

/**
 * Non-argument command "exit". Close the application without save changes
 */

public class ExitCommand extends NonArgsCommand {

    /**
     * Standard constructor, sets name and description of command
     */
    ExitCommand() {
        super("exit", "exit : завершить программу (без сохранения в файл)");
    }

    /**
     * Closes this app
     *
     * @param reader reader of file from that gives data, if null data gives from console
     */
    @Override
    public void execute(InputScriptReader reader) {
        System.exit(0);
    }
}