package database;

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

/**
 * Do all work between database and server
 */
public class DataBaseManager {
    private static final Logger LOGGER = LogManager.getLogger(DataBaseManager.class);
    private final Connection conn;

    public DataBaseManager(Connection conn) {
        this.conn = conn;
    }


    /**
     * Uploads all dragons from saved collection from database to server
     *
     * @param collection new dragon collection
     */
    public void uploadCollection(DragonCollection collection) {
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

    /**
     * Parses dragon from database (table 'dragons')
     *
     * @param dragon resultSet of query to table 'dragons'
     * @return new dragon object
     * @throws SQLException   if something went wrong with connection
     * @throws ParseException if there is wrong data in database
     */
    private Dragon createDragon(ResultSet dragon) throws SQLException, ParseException {
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

    /**
     * Adds new user in database (table 'users') if it's not exists yet
     *
     * @param user new user
     * @throws LoginCollisionException if such user already in database
     * @throws SQLException            if something went wrong with connection
     */
    public void addUser(User user) throws LoginCollisionException, SQLException {
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

    /**
     * Checks for this user in database (table 'users').
     *
     * @param user current user
     * @throws NoSuchUserException    if there are no such user signed up
     * @throws SQLException           if something went wrong with connection
     * @throws WrongPasswordException if password in database and current password is not equals
     */
    public void signInUser(User user) throws NoSuchUserException, SQLException, WrongPasswordException {
        PreparedStatement stat = conn.prepareStatement("SELECT * FROM users WHERE login = ?");
        stat.setString(1, user.getLogin());
        ResultSet dbUser = stat.executeQuery();
        if (!dbUser.next()) throw new NoSuchUserException();
        String dbPasswd = dbUser.getString("passwd");
        String salt = dbUser.getString("salt");
        if (!dbPasswd.equals(preparePasswd(user.getHashedPasswd(), salt)))
            throw new WrongPasswordException();
    }

    /**
     * Adds new dragon object to the database (table 'dragons')
     *
     * @param dragon current dragon
     * @return dragon id, generated by database
     * @throws SQLException if something went wrong with connection
     */
    public int addDragon(Dragon dragon) throws SQLException {
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

    /**
     * Deletes all dragons, created by current user from the database (table 'dragons')
     *
     * @param user current user
     * @throws SQLException if something went wrong with connection
     */
    public void clearDragons(User user) throws SQLException {
        String login = user.getLogin();
        PreparedStatement stat = conn.prepareStatement("DELETE FROM dragons WHERE creator = ?");
        stat.setString(1, login);
        stat.executeUpdate();
    }

    /**
     * Removes dragon from database (table 'dragons') by its id.
     * Checks that creator of this dragon was current user
     *
     * @param id   current dragon's id
     * @param user current user
     * @throws SQLException              if something went wrong with connection
     * @throws PermissionDeniedException if login of current user is not equals to creator login
     */
    public void removeById(int id, User user) throws SQLException, PermissionDeniedException {
        String login = user.getLogin();
        PreparedStatement stat = conn.prepareStatement("DELETE FROM dragons WHERE id = ? AND creator = ?");
        stat.setInt(1, id);
        stat.setString(2, login);
        int deleted = stat.executeUpdate();
        if (deleted == 0) throw new PermissionDeniedException();
    }

    /**
     * Removes current dragon from database (table 'dragons').
     * Used in commands 'remove_greater' and 'remove_lower'.
     * Just ignores PermissionDenied
     *
     * @param dragon current dragon
     * @param user   current user
     * @throws SQLException if something went wrong with connection
     */
    public void removeDragon(Dragon dragon, User user) throws SQLException {
        try {
            removeById(dragon.hashCode(), user);
        } catch (PermissionDeniedException ignored) {
        }
    }

    /**
     * Changes one dragon to another, keeping its id in database (table 'dragons')
     *
     * @param id        current id of dragon
     * @param newDragon on that dragon database will be updated
     * @param user      current user
     * @throws SQLException              if something went wrong with connection
     * @throws PermissionDeniedException if login of current user is not equals to creator of old dragon login
     */
    public void updateDragon(int id, Dragon newDragon, User user)
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

    /**
     * Changes password on current user in database (table 'users')
     *
     * @param user      current user
     * @param newPasswd new password
     * @throws SQLException if something went wrong with connection
     */
    public void changePasswd(User user, String newPasswd) throws SQLException {
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

    /**
     * Hashes password using MD5 algorithm, pepper and this salt
     *
     * @param passwd non-hashed password
     * @param salt   current salt
     * @return hashed password
     */
    private String preparePasswd(String passwd, String salt) {
        String pepper = System.getenv("pepper");
        passwd += salt + pepper;
        for (int i = 0; i < 500; i++) {
            passwd = User.getMD5Hash(passwd);
        }
        return passwd;
    }

    /**
     * Hashes password and randomly generates salt for it
     *
     * @param passwd non-hashed password
     * @return (hashedPassword, salt)
     */
    private Pair<String, String> preparePasswd(String passwd) {
        String salt = RandomStringUtils.randomAscii(16);
        passwd = preparePasswd(passwd, salt);
        return new ImmutablePair<>(passwd, salt);
    }
}