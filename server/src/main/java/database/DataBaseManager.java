package database;

import collection.DragonCollection;
import dragons.*;
import exceptions.LoginCollisionException;
import exceptions.NoSuchUserException;
import exceptions.PermissionDeniedException;
import exceptions.WrongPasswordException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
            LOGGER.error("Something wrong with database! " + e.getMessage());
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
        stat = conn.prepareStatement("INSERT INTO users(login, passwd, salt) VALUES (?, ?, ?)");
        Pair<String, String> passwdPair = preparePasswd(user.getHashedPasswd());
        stat.setString(1, user.getLogin());
        stat.setString(2, passwdPair.getLeft());
        stat.setString(3, passwdPair.getRight());
        stat.executeUpdate();
    }

    public static void signInUser(Connection conn, User user) throws NoSuchUserException, SQLException, WrongPasswordException {
        PreparedStatement stat = conn.prepareStatement("SELECT * FROM users WHERE login = ?");
        stat.setString(1, user.getLogin());
        ResultSet dbUser = stat.executeQuery();
        if (!dbUser.next()) throw new NoSuchUserException();
        String dbLogin = dbUser.getString("login");
        String dbPasswd = dbUser.getString("passwd");
        String salt = dbUser.getString("salt");
        if (!dbLogin.equals(user.getLogin()) || !dbPasswd.equals(preparePasswd(user.getHashedPasswd(), salt).getLeft()))
            throw new WrongPasswordException();
    }

    public static int addDragon(Connection conn, Dragon dragon) throws SQLException {
        PreparedStatement stat = conn.prepareStatement("INSERT INTO dragons" +
                "(name, x, y, creation_date, age, weight, color, character, eyes_count, creator)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?::color, ?::dr_char, ?, ?) RETURNING id");
        Timestamp creationDate = new Timestamp((dragon.getCreationDate().getTime() / 1000) * 1000);
        int age = dragon.getAge();

        stat.setString(1, dragon.getName());
        stat.setDouble(2, dragon.getCoordinates().getX());
        stat.setFloat(3, dragon.getCoordinates().getY());
        stat.setTimestamp(4, creationDate);
        if (age == -1) stat.setNull(5, Types.INTEGER);
        else stat.setInt(5, age);
        stat.setLong(6, dragon.getWeight());
        stat.setString(7, dragon.getColor().name());
        stat.setString(8, dragon.getDragonCharacter().name());
        stat.setFloat(9, dragon.getDragonHead().getEyesCount());
        stat.setString(10, dragon.getCreator().getLogin());

        ResultSet set = stat.executeQuery();
        set.next();
        return set.getInt("id");
    }

    public static void insertDragon(Connection conn, Dragon dragon) throws SQLException {
        PreparedStatement stat = conn.prepareStatement("INSERT INTO dragons" +
                "(id, name, x, y, creation_date, age, weight, color, character, eyes_count, creator)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?::color, ?::dr_char, ?, ?)");
        Timestamp creationDate = new Timestamp((dragon.getCreationDate().getTime() / 1000) * 1000);
        int age = dragon.getAge();

        stat.setInt(1, dragon.hashCode());
        stat.setString(2, dragon.getName());
        stat.setDouble(3, dragon.getCoordinates().getX());
        stat.setFloat(4, dragon.getCoordinates().getY());
        stat.setTimestamp(5, creationDate);
        if (age == -1) stat.setNull(6, Types.INTEGER);
        else stat.setInt(6, age);
        stat.setLong(7, dragon.getWeight());
        stat.setString(8, dragon.getColor().name());
        stat.setString(9, dragon.getDragonCharacter().name());
        stat.setFloat(10, dragon.getDragonHead().getEyesCount());
        stat.setString(11, dragon.getCreator().getLogin());

        stat.executeUpdate();
    }

    public static void clearDragons(Connection conn, User user) throws SQLException {
        String login = user.getLogin();
        PreparedStatement stat = conn.prepareStatement("DELETE FROM dragons WHERE creator = ?");
        stat.setString(1, login);
        stat.executeUpdate();
    }

    public static void removeById(Connection conn, int id, User user) throws SQLException, PermissionDeniedException {
        String login = user.getLogin();
        PreparedStatement stat = conn.prepareStatement("DELETE FROM dragons WHERE id = ? AND creator = ?");
        stat.setInt(1, id);
        stat.setString(2, login);
        int deleted = stat.executeUpdate();
        if (deleted == 0) throw new PermissionDeniedException();
    }

    public static void removeDragon(Connection conn, Dragon dragon, User user) throws SQLException {
        try {
            removeById(conn, dragon.hashCode(), user);
        } catch (PermissionDeniedException ignored) {
        }
    }

    public static void updateDragon(Connection conn, int id, Dragon newDragon, User user)
            throws SQLException, PermissionDeniedException {
        String login = user.getLogin();
        PreparedStatement stat = conn.prepareStatement("UPDATE dragons " +
                "SET name = ?, " +
                "x = ?, " +
                "y = ?, " +
                "age = ?, " +
                "weight = ?, " +
                "color = ?::color, " +
                "character = ?::dr_char," +
                "eyes_count = ? " +
                "WHERE id = ? AND creator = ?");
        stat.setString(1, newDragon.getName());
        stat.setDouble(2, newDragon.getCoordinates().getX());
        stat.setFloat(3, newDragon.getCoordinates().getY());
        int age = newDragon.getAge();
        if (age == -1) stat.setNull(4, Types.INTEGER);
        else stat.setInt(4, age);
        stat.setLong(5, newDragon.getWeight());
        stat.setString(6, newDragon.getColor().name());
        stat.setString(7, newDragon.getDragonCharacter().name());
        stat.setFloat(8, newDragon.getDragonHead().getEyesCount());
        stat.setInt(9, id);
        stat.setString(10, login);
        int updated = stat.executeUpdate();
        if (updated == 0) throw new PermissionDeniedException();
    }

    public static void changePasswd(Connection conn, User user, String newPasswd) throws SQLException {
        Pair<String, String> passwdPair = preparePasswd(newPasswd);
        newPasswd = passwdPair.getLeft();
        String salt = passwdPair.getRight();
        String login = user.getLogin();
        PreparedStatement stat = conn.prepareStatement("UPDATE users SET passwd = ?, salt = ? WHERE login = ?");
        stat.setString(1, newPasswd);
        stat.setString(2, salt);
        stat.setString(3, login);
        stat.executeUpdate();
    }

    private static Pair<String, String> preparePasswd(String passwd, String salt) {
        String pepper = "E1!(I)[!kv3\\\\8T ";
        passwd += salt + pepper;
        for (int i = 0; i < 500; i++) {
            passwd = User.getMD5Hash(passwd);
        }
        return new ImmutablePair<>(passwd, salt);
    }

    private static Pair<String, String> preparePasswd(String passwd) {
        String salt = RandomStringUtils.randomAscii(16);
        return preparePasswd(passwd, salt);
    }
}