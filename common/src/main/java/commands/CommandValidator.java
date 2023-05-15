package commands;

import dragons.Dragon;
import exceptions.IncorrectInputException;
import network.Request;
import parsers.MyScanner;
import user.User;

public abstract class CommandValidator {

    /**
     * Validates command from string
     *
     * @return request, built from line
     * @throws IncorrectInputException if something wrong with inputted line
     */
    public static Request validCommand(String line, MyScanner reader, User user) throws IncorrectInputException {
        String[] input = line.split(" ");
        if (input.length > 2) throw new IncorrectInputException("Unknown command");
        CommandType command = CommandType.getByName(input[0]);
        Object arg = null;
        Dragon dragon = null;
        if (command.isHaveArgs()) {
            if (input.length != 2) throw new IncorrectInputException("Unknown command");
            Class type = command.getArgClass();
            arg = genArg(input[1], type);
        }
        if (command.isNeedReadDragon()) dragon = genDragon(reader, user);
        return new Request(command, arg, dragon, user);
    }

    /**
     * Reads argument and checks its type
     *
     * @return argument
     * @throws IncorrectInputException if something wrong with inputted line
     */
    private static Object genArg(String argStr, Class type) {
        try {
            return switch (type.getSimpleName()) {
                case "int" -> Integer.parseInt(argStr);
                case "long" -> Long.parseLong(argStr);
                default -> argStr;
            };
        } catch (ClassCastException | NumberFormatException e) {
            throw new IncorrectInputException("Unknown command");
        }
    }

    /**
     * Reads dragon from script or from console, if reader is null
     *
     * @return generated dragon object
     */
    private static Dragon genDragon(MyScanner reader, User user) {
        Dragon dragon = reader.readDragon(user);
        if (dragon == null) throw new IncorrectInputException("Unknown command");
        else return dragon;
    }

    /**
     * Validates command request
     *
     * @return true if all ok
     */
    public static boolean validCommand(Request request) {
        CommandType command = request.command();
        Object arg = request.arg();
        Dragon dragon = request.dragon();
        if (command == null) return false;
        if (command.isHaveArgs()) {
            Class type = command.getArgClass();
            if (type == null) return false;
            if (arg == null) return false;
            if (!checkType(arg, type)) return false;
        }
        return !command.isNeedReadDragon() || dragon != null;
    }

    /**
     * Checks type of argument in request
     *
     * @return true if all ok
     */
    private static boolean checkType(Object arg, Class type) {
        return switch (type.getSimpleName()) {
            case "int" -> arg.getClass().getSimpleName().equals("Integer");
            case "long" -> arg.getClass().getSimpleName().equals("Long");
            default -> arg.getClass().getSimpleName().equals("String");
        };
    }
}
