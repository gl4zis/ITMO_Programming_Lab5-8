package client;

import parsers.InputConsoleReader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    // Sets system properties for logger configuration and for saving log files
    static {
        String creationDate = new SimpleDateFormat("yyyy-MM-dd/HH-mm-ss").format(new Date());
        System.setProperty("logs.path", "./lab5-8-client-logs/" + creationDate + ".log");
    }

    public static void main(String[] args) {
        Connection connection = new Connection(6789);
        boolean work = true;
        while (work) {
            connection.run();
            try {
                if (System.in.available() > 0) {
                    if (InputConsoleReader.readNextLine().equals("exit")) work = false;
                }
            } catch (IOException ignored) {
                work = false;
            }
        }
    }
}