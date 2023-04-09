package network;

import dragons.Dragon;
import exceptions.EmptyInputException;
import exceptions.IncorrectDataException;
import exceptions.IncorrectInputException;
import parsers.InputConsoleReader;

import java.io.Serializable;

public class Request implements Serializable {
    private final CommandType command;
    private final String arg;
    private final Dragon dragon;

    public Request(String line) throws EmptyInputException {
        command = getCommandType(line);
        arg = getArg(line);
        dragon = getDragon(line);
    }

    private CommandType getCommandType(String line) throws EmptyInputException {
        String[] input = line.split(" ");
        if (input.length > 2) throw new IncorrectInputException("Unknown command");
        else if (input.length == 0) throw new EmptyInputException();
        return CommandType.getByName(input[0]);
    }

    private String getArg(String line) {
        String[] input = line.split(" ");
        try {
            if (command.isHaveArgs()) {
                return input[1];
            } else {
                if (input.length > 1) throw new IncorrectInputException("Unknown command");
                return null;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IncorrectInputException("Unknown command");
        }
    }

    private Dragon getDragon(String line) {
        if (command.isNeedReadDragon()) {
            return InputConsoleReader.readDragon();
        } else return null;
    }

    public CommandType getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }

    public Dragon getDragon() {
        return dragon;
    }
}
