import exceptions.IncorrectInputException;
import exceptions.UnavailableServerException;
import parsers.InputScriptReader;
import parsers.StringModificator;

import java.io.FileNotFoundException;
import java.util.HashSet;

public abstract class CommandProcessor {
    public static String execute(Connection conn, String line) {
        if (line.trim().isEmpty())
            return "";
        return execute(conn, line, null);
    }

    private static String execute(Connection conn, String line, InputScriptReader reader) {
        try {
            switch (line.split(" ")[0]) {
                case "exit":
                    return null;
                case "help":
                    return help(conn);
                case "update":
                    return update(conn, line, reader);
                case "execute_script":
                    if (ex_script(conn, line))
                        return null;
                    return "";
                default:
                    return conn.sendReqGetResp(line, reader);
            }
        } catch (IncorrectInputException | UnavailableServerException e) {
            return e.getMessage();
        }
    }

    private static String help(Connection conn) throws UnavailableServerException {
        String output = conn.sendReqGetResp("help");
        output += """
                        
                \texit : terminate the program
                \texecute_script filepath : execute script in the file, by its filepath""";
        return output;
    }

    private static String update(Connection conn, String line, InputScriptReader reader) throws UnavailableServerException {
        String find = "find" + line.substring(6);
        String output = conn.sendReqGetResp(find);
        if (!output.startsWith("No such")) {
            return conn.sendReqGetResp(line, reader);
        } else return output;
    }

    private static boolean ex_script(Connection conn, String line) throws UnavailableServerException {
        return validateScript(conn, new HashSet<>(), line);
    }

    private static boolean ex_script(Connection conn, HashSet<String> files, InputScriptReader reader)
            throws UnavailableServerException {
        String line = reader.readNextLine();
        while (line != null) {
            if (!line.trim().isEmpty()) {
                try {
                    if (line.startsWith("execute_script")) {
                        validateScript(conn, files, line);
                    } else {
                        String output = execute(conn, line, reader);
                        System.out.println("Executing: " + line);
                        if (output == null)
                            return false;
                        System.out.println(output);
                    }
                } catch (IncorrectInputException ignored) {
                }
            }
            line = reader.readNextLine();
        }
        return true;
    }

    private static boolean validateScript(Connection conn, HashSet<String> files, String line) throws UnavailableServerException {
        String filePath;
        InputScriptReader reader;
        try {
            filePath = StringModificator.filePathFormat(line.split(" ")[1]);
            if (!files.contains(filePath)) {
                files.add(filePath);
                reader = new InputScriptReader(filePath);
                System.out.println("Executing script: " + filePath);
            } else {
                System.out.println("Try of recursion! Script wouldn't execute");
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IncorrectInputException("Unknown command");
        } catch (FileNotFoundException | SecurityException e) {
            throw new IncorrectInputException("File don't exist or there are no enough rights");
        }

        boolean exit = !ex_script(conn, files, reader);

        files.remove(filePath);
        System.out.printf("Script: %s executing ended\n", filePath);
        return exit;
    }
}
