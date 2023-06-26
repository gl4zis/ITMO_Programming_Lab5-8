package database;

import exceptions.ExitException;
import general.OsUtilus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        String dbFilePath = System.getProperty("user.home") + "/database.cfg";
        try {
            InputStream stream = new FileInputStream(dbFilePath);
            config.load(stream);
            return DriverManager.getConnection((String) config.get("URL"), getUser(config), getPassword(config));
        } catch (SQLException | NullPointerException | IOException e) {
            LOGGER.fatal("Can't connect to the data base! " + e.getMessage());
            throw new ExitException();
        }
    }

    private static String getUser(Properties config) {
        return (String) config.get("user");
    }

    private static String getPassword(Properties config) {
        return (String) config.get("password");
    }
}