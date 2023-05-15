package commands;

import client.ClientConnection;
import exceptions.ExitException;
import exceptions.IncorrectInputException;
import parsers.MyScanner;

import java.util.HashMap;
import java.util.Map;

/**
 * Preprocessing commands on client
 */
public class CommandProcessor {
    private static final MyScanner CONSOLE = new MyScanner(System.in);
    private final Map<String, Command> commands;
    private final ClientConnection conn;

    /**
     * Constructor sets ClientConnection and fills commands HashMap
     */
    public CommandProcessor(ClientConnection conn) {
        this.conn = conn;
        commands = new HashMap<>();
        Command changePasswd = new ChangePasswordCommand(conn);
        Command exScript = new ExecuteScriptCommand(conn);
        Command exit = new ExitCommand(conn);
        Command help = new HelpCommand(conn);
        Command ping = new PingCommand(conn);
        Command signOut = new SignOutCommand(conn);
        Command update = new UpdateCommand(conn);
        commands.put(changePasswd.getName(), changePasswd);
        commands.put(exit.getName(), exit);
        commands.put(exScript.getName(), exScript);
        commands.put(help.getName(), help);
        commands.put(ping.getName(), ping);
        commands.put(signOut.getName(), signOut);
        commands.put(update.getName(), update);
    }

    /**
     * Executes command from console line
     */
    public void execute() {
        String line = CONSOLE.nextLine();
        if (!line.trim().isEmpty()) {
            String output = execute(line, CONSOLE);
            if (output == null) throw new ExitException();
            else System.out.println(output);
        }
    }

    /**
     * Executes command from any line
     *
     * @param line string with command
     * @return response from server
     */
    String execute(String line, MyScanner reader) {
        try {
            String command = line.split(" ")[0];
            if (command.equals("sign_up") || command.equals("sign_in"))
                throw new IncorrectInputException("Unknown command");
            if (commands.get(command) == null) {
                return conn.sendReqGetResp(line, reader);
            } else return commands.get(command).execute(line, reader);
        } catch (IncorrectInputException e) {
            return e.getMessage();
        }
    }
}