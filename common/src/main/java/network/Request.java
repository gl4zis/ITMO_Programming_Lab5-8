package network;

import dragons.Dragon;
import exceptions.IncorrectDataException;
import exceptions.IncorrectInputException;
import parsers.InputConsoleReader;
import parsers.InputScriptReader;

import java.io.Serializable;

/**
 * Request from client to server.
 * Includes commandType, argument, dragon object
 */
public class Request implements Serializable {
    private final CommandType command;
    private final Object arg;
    private final Dragon dragon;

    /**
     * For sending commands from script file | Request(line, null) if commands executes from console
     *
     * @param reader script parser
     */
    public Request(String line, InputScriptReader reader) {
        command = genCommandType(line);
        arg = genArg(line);
        if (reader == null) {
            dragon = genDragon();
        } else
            dragon = genDragon(reader);
    }

    /**
     * Generates commandType from line
     */
    private CommandType genCommandType(String line) {
        String[] input = line.split(" ");
        if (input.length > 2) throw new IncorrectInputException("Unknown command");
        return CommandType.getByName(input[0]);
    }

    /**
     * Generates argument from line
     */
    private Object genArg(String line) {
        String[] input = line.split(" ");
        try {
            if (command.isHaveArgs()) {
                String argStr = input[1].trim();
                return switch (command.argClass().getSimpleName()) {
                    case "int" -> Integer.parseInt(argStr);
                    case "long" -> Long.parseLong(argStr);
                    default -> argStr;
                };
            } else {
                if (input.length > 1) throw new IncorrectInputException("Unknown command");
                return null;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IncorrectInputException("Unknown command");
        } catch (NumberFormatException e) {
            throw new IncorrectInputException("Incorrect command argument");
        }
    }

    /**
     * Generates dragon from line (console command)
     */
    private Dragon genDragon() {
        if (command.isNeedReadDragon()) {
            return InputConsoleReader.readDragon();
        } else return null;
    }

    /**
     * Generates dragon from line (script command)
     */
    private Dragon genDragon(InputScriptReader reader) {
        if (command.isNeedReadDragon()) {
            Dragon dragon = reader.readDragon();
            if (dragon != null) return dragon;
            else throw new IncorrectDataException("Incorrect dragon profile");
        } else return null;
    }

    public CommandType getCommand() {
        return command;
    }

    public Object getArg() {
        return arg;
    }

    public Dragon getDragon() {
        return dragon;
    }
}
