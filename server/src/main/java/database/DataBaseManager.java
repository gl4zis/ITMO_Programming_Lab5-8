package database;

import collection.DragonCollection;
import dragons.*;
import exceptions.LoginCollisionException;
import exceptions.NoSuchUserException;
import exceptions.WrongPasswordException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.DateParser;
import user.User;

import java.sql.*;
import java.text.ParseException;
import java.util.Date;

public abstract class DataBaseManager {
    private static final Logger LOGGER = LogManager.getLogger(DataBaseManager.class);

    public static void uploadCollection(Connection conn, DragonCollection collection) {
        String query = "SELECT * FROM dragons JOIN users ON creator = login;";
        try {
            ResultSet dragons = conn.createStatement().executeQuery(query);
            while (dragons.next()) {
                collection.add(createDragon(dragons));
            }
        } catch (SQLException | ParseException e) {
            LOGGER.error("Something wrong with data base! " + e.getMessage());
            LOGGER.error("Collection wasn't upload");
        }
    }

    private static Dragon createDragon(ResultSet dragon) throws SQLException, ParseException {
        int id = dragon.getInt("id");
        String name = dragon.getString("name");
        double X = dragon.getDouble("x");
        float Y = dragon.getFloat("y");
        Coordinates coords = new Coordinates(X, Y);
        String date = dragon.getString("creation_date");
        Date creationDate = DateParser.stringToDate(date);
        int age = dragon.getInt("age");
        long weight = dragon.getLong("weight");
        Color color = Color.valueOf(dragon.getString("color"));
        DragonCharacter character = DragonCharacter.valueOf(dragon.getString("character"));
        float eyesCount = dragon.getFloat("eyes_count");
        DragonHead head = new DragonHead(eyesCount);
        String login = dragon.getString("login");
        String passwd = dragon.getString("passwd");
        User creator = User.signIn(login, passwd);

        Dragon newDragon = new Dragon(id, name, coords, creationDate, weight, color, character, head, creator);
        if (age > 0) newDragon.setAge(age);
        return newDragon;
    }

    public static void addUser(Connection conn, User user) throws LoginCollisionException, SQLException {
        PreparedStatement stat = conn.prepareStatement("SELECT login FROM users WHERE login = ?");
        stat.setString(1, user.getLogin());
        ResultSet login = stat.executeQuery();
        if (login.next()) throw new LoginCollisionException();
        stat = conn.prepareStatement("INSERT INTO users(login, passwd) VALUES (?, ?)");
        stat.setString(1, user.getLogin());
        stat.setString(2, user.getHashedPasswd());
        stat.executeUpdate();
    }

    public static void signInUser(Connection conn, User user) throws NoSuchUserException, SQLException, WrongPasswordException {
        PreparedStatement stat = conn.prepareStatement("SELECT * FROM users WHERE login = ?");
        stat.setString(1, user.getLogin());
        ResultSet dbUser = stat.executeQuery();
        if (!dbUser.next()) throw new NoSuchUserException();
        String dbLogin = dbUser.getString("login");
        String dbPasswd = dbUser.getString("passwd");
        if (!dbLogin.equals(user.getLogin()) || !dbPasswd.equals(user.getHashedPasswd()))
            throw new WrongPasswordException();
    }

    public static int addDragon(Connection conn, Dragon dragon) throws SQLException {
        PreparedStatement stat = conn.prepareStatement("INSERT INTO dragons" +
                "(name, x, y, creation_date, age, weight, color, character, eyes_count, creator)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?::color, ?::dr_char, ?, ?) RETURNING id");
        String name = dragon.getName();
        double x = dragon.getCoordinates().getX();
        float y = dragon.getCoordinates().getY();
        java.sql.Date creationDate = new java.sql.Date(dragon.getCreationDate().getTime());
        Integer age = dragon.getAge();
        if (age == -1) age = null;
        long weight = dragon.getWeight();
        Color color = dragon.getColor();
        DragonCharacter character = dragon.getDragonCharacter();
        float eyesCount = dragon.getDragonHead().getEyesCount();
        User creator = dragon.getCreator();

        stat.setString(1, name);
        stat.setDouble(2, x);
        stat.setFloat(3, y);
        stat.setDate(4, creationDate);
        stat.setInt(5, age);
        stat.setLong(6, weight);
        stat.setString(7, color.name());
        stat.setString(8, character.name());
        stat.setFloat(9, eyesCount);
        stat.setString(10, creator.getLogin());

        ResultSet set = stat.executeQuery();
        set.next();
        return set.getInt("id");
    }
}
