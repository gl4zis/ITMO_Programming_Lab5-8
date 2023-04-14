package commands;

import dragons.Dragon;
import exceptions.IncorrectInputException;
import network.Request;
import parsers.InputConsoleReader;
import parsers.InputScriptReader;

public abstract class CommandValidator {

    public static Request validCommand(String line, InputScriptReader reader) throws IncorrectInputException {
        String[] input = line.split(" ");
        if (input.length > 2) throw new IncorrectInputException("Unknown command");
        CommandType command = CommandType.getByName(input[0]);
        Object arg = null;
        Dragon dragon = null;
        if (command.isHaveArgs()) {
            Class type = command.argClass();
            arg = genArg(input[1], type);
        }
        if (command.isNeedReadDragon()) dragon = genDragon(reader);
        return new Request(command, arg, dragon);
    }

    private static Object genArg(String argStr, Class type) {
        return switch (type.getSimpleName()) {
            case "int" -> Integer.parseInt(argStr);
            case "long" -> Long.parseLong(argStr);
            default -> argStr;
        };
    }

    private static Dragon genDragon(InputScriptReader reader) {
        if (reader == null) return InputConsoleReader.readDragon();
        else return reader.readDragon();
    }

    public static boolean validCommand(Request request) {
        CommandType command = request.command();
        Object arg = request.arg();
        Dragon dragon = request.dragon();
        if (command == null) return false;
        if (command.isHaveArgs()) {
            Class type = command.argClass();
            if (type == null) return false;
            if (!checkType(arg, type)) return false;
        }
        return !command.isNeedReadDragon() || dragon != null;
    }

    private static boolean checkType(Object arg, Class type) {
        return switch (type.getSimpleName()) {
            case "int" -> arg.getClass().getSimpleName().equals("Integer");
            case "long" -> arg.getClass().getSimpleName().equals("Long");
            default -> arg.getClass().getSimpleName().equals("String");
        };
    }
}
