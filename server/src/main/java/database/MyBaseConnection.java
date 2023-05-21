package database;

import exceptions.ExitException;
import general.OsUtilus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.StringModificator;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class MyBaseConnection {
    private static final Logger LOGGER = LogManager.getLogger(MyBaseConnection.class);

    /**
     * Do connection with my database, using JDBC PostgreSQL
     *
     * @return database connection
     */
    public static Connection connect() {
        try {
            return DriverManager.getConnection(getUrl(), getInfo());
        } catch (SQLException e) {
            LOGGER.fatal("Can't connect to the data base! " + e.getMessage());
            throw new ExitException();
        }
    }

    /**
     * @return URL for database connection
     */
    private static String getUrl() {
        if (OsUtilus.IsWindows())
            return "jdbc:postgresql://localhost:7812/studs";
        else return "jdbc:postgresql://pg:5432/studs";
    }

    /**
     * @return Properties with user and password for connection
     */
    private static Properties getInfo() {
        Properties info = new Properties();
        try {
            if (OsUtilus.IsWindows())
                info.load(new FileInputStream("C:\\Windows\\db\\db.cfg"));
            else info.load(parsePgPass());
        } catch (IOException | SecurityException e) {
            LOGGER.error("Something went wrong with config file =(");
        }
        return info;
    }

    /**
     * Parses file '~/.pgpass'
     *
     * @return InputStream with user and password
     * @throws FileNotFoundException if file is not exists
     */
    private static InputStream parsePgPass() throws FileNotFoundException {
        File script = new File(StringModificator.filePathFormat("~/.pgpass"));
        FileInputStream in = new FileInputStream(script);
        InputStreamReader reader = new InputStreamReader(in);

        StringBuilder line = new StringBuilder();
        try {
            int nextSym = reader.read();
            while (nextSym != -1) {
                line.append((char) nextSym);
                nextSym = reader.read();
            }
        } catch (IOException e) {
            LOGGER.error("Something wrong with config file =(");
            return new ByteArrayInputStream("".getBytes());
        }

        String[] data = line.toString().split(":");
        String user = "user = " + data[data.length - 2];
        String passwd = "password = " + data[data.length - 1];
        return new ByteArrayInputStream((user + "\n" + passwd).getBytes());
    }
}