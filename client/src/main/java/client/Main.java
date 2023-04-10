package client;

import parsers.InputConsoleReader;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    // Sets system properties for logger configuration and for saving log files
    static {
        String creationDate = new SimpleDateFormat("yyyy-MM-dd/HH-mm-ss").format(new Date());
        System.setProperty("logs.path", "./lab5-8-client-logs/" + creationDate + ".log");
    }

    public static void main(String[] args) {
        try {
            Connection connection = new Connection(InetAddress.getLocalHost(), 6789);
            connection.run();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}