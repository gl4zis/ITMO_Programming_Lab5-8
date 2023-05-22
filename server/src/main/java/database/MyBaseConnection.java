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
        Properties config = new Properties();
        try {
            InputStream stream = DataBaseManager.class.getClassLoader().getResourceAsStream("database.cfg");
            config.load(stream);
            return DriverManager.getConnection(getUrl(config), getUser(config), getPassword(config));
        } catch (SQLException | NullPointerException | IOException e) {
            LOGGER.fatal("Can't connect to the data base! " + e.getMessage());
            throw new ExitException();
        }
    }

    /**
     * @return URL for database connection
     */
    private static String getUrl(Properties config) {
        if (OsUtilus.IsWindows())
            return (String) config.get("WINDOWS_URL");
        else
            return (String) config.get("HELIOS_URL");
    }

    private static String getUser(Properties config) {
        return (String) config.get("user");
    }

    private static String getPassword(Properties config) {
        return (String) config.get("password");
    }
}