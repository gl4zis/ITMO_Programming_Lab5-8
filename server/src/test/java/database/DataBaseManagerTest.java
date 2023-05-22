package database;

import collection.DragonCollection;
import dragons.Color;
import dragons.Coordinates;
import dragons.Dragon;
import dragons.DragonHead;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import static dragons.DragonCharacter.WISE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DataBaseManagerTest {
    private Connection conn;
    private Statement stat;
    private PreparedStatement prepStat;
    private ResultSet set;
    private User admin;
    private Dragon dragon;
    private DataBaseManager baseMan;

    @BeforeEach
    void init() throws SQLException {
        admin = User.signUp("admin", "qwerty");
        dragon = new Dragon(123, "dragonTest", new Coordinates(0.01, 1000),
                new Date(0), 1000, Color.RED, WISE, new DragonHead(3), admin);

        conn = Mockito.mock(Connection.class);
        stat = Mockito.mock(Statement.class);
        prepStat = Mockito.mock(PreparedStatement.class);
        set = Mockito.mock(ResultSet.class);
        baseMan = new DataBaseManager(conn);
        Mockito.when(conn.createStatement()).thenReturn(stat);
        Mockito.when(conn.prepareStatement(Mockito.anyString())).thenReturn(prepStat);
        Mockito.when(stat.executeQuery(Mockito.anyString())).thenReturn(set);
        Mockito.when(prepStat.executeQuery()).thenReturn(set);

        ArrayList<Boolean> array = new ArrayList<>();
        array.add(true);
        array.add(false);
        Iterator<Boolean> iter = array.iterator();
        Mockito.when(set.next()).thenReturn(iter.next());

        Mockito.when(set.getInt("id")).thenReturn(123);
        Mockito.when(set.getString("key")).thenReturn("key");
        Mockito.when(set.getString("name")).thenReturn("dragonTest");
        Mockito.when(set.getDouble("x")).thenReturn(0.01);
        Mockito.when(set.getFloat("y")).thenReturn(1000f);
        Mockito.when(set.getString("creation_date")).thenReturn("1970-01-01 03:00:00");
        Mockito.when(set.getInt("age")).thenReturn(10);
        Mockito.when(set.getLong("weight")).thenReturn(1000L);
        Mockito.when(set.getString("color")).thenReturn("RED");
        Mockito.when(set.getString("character")).thenReturn("WISE");
        Mockito.when(set.getFloat("eyes_count")).thenReturn(3.0f);
        Mockito.when(set.getString("login")).thenReturn("admin");
        Mockito.when(set.getString("passwd")).thenReturn("4470e980d181565e5f2f7ab5e223ab0c");
        Mockito.when(set.getString("salt")).thenReturn("T1fD\"L;seV/M3Io_");
    }

    @Test
    void uploadCollection() {
        DragonCollection collection = new DragonCollection();
        try {
            baseMan.uploadCollection(collection);
        } catch (IdCollisionException ignored) {
        }
        assertEquals(1, collection.getItems().size());
        assertEquals(dragon, collection.getItems().toArray()[0]);
    }

    @Test
    void addUser() throws LoginCollisionException, SQLException {
        assertThrows(LoginCollisionException.class, () -> baseMan.addUser(admin));
        Mockito.when(set.next()).thenReturn(false);
        baseMan.addUser(admin);
    }

    @Test
    void signInUser() throws SQLException, NoSuchUserException, WrongPasswordException {
        baseMan.signInUser(admin);
        admin = User.signUp("admin", "111");
        assertThrows(WrongPasswordException.class, () -> baseMan.signInUser(admin));
        Mockito.when(set.next()).thenReturn(false);
        assertThrows(NoSuchUserException.class, () -> baseMan.signInUser(admin));
    }

    @Test
    void addDragon() throws SQLException {
        int id = baseMan.addDragon(dragon);
        assertEquals(123, id);
    }

    @Test
    void clearDragons() throws SQLException {
        baseMan.clearDragons(admin);
    }

    @Test
    void removeById() throws SQLException, PermissionDeniedException {
        assertThrows(PermissionDeniedException.class, () -> baseMan.removeById(123, admin));
        Mockito.when(prepStat.executeUpdate()).thenReturn(1);
        baseMan.removeById(123, admin);
    }

    @Test
    void removeDragon() throws SQLException {
        baseMan.removeDragon(dragon, admin);
    }

    @Test
    void updateDragon() throws PermissionDeniedException, SQLException {
        assertThrows(PermissionDeniedException.class, () -> baseMan.updateDragon(1, dragon, admin));
        Mockito.when(prepStat.executeUpdate()).thenReturn(1);
        baseMan.updateDragon(1, dragon, admin);
    }

    @Test
    void changePasswd() throws SQLException {
        baseMan.changePasswd(admin, "12345");
    }
}