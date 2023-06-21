package commands;

import database.DataBaseManager;
import dragons.*;
import exceptions.*;
import network.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import user.User;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    private CommandManager manager;
    private Dragon dragon;
    private User admin;

    @BeforeEach
    void setUp() {
        admin = User.signUp("admin", "qwerty");
        dragon = new Dragon("test", new Coordinates(0, 0), 100, Color.RED, DragonCharacter.WISE, new DragonHead(1), admin);
        DataBaseManager baseMan = Mockito.mock(DataBaseManager.class);
        DragonCollection collection = new DragonCollection();
        manager = new CommandManager(baseMan, collection);
    }

    @Test
    void add() throws SQLException {
        Mockito.when(manager.getBaseMan().addDragon(Mockito.any())).thenReturn(1);
        Request addReq = new Request(CommandType.ADD, null, dragon, admin);
        assertEquals("Dragon successfully added in the collection", manager.seekCommand(addReq));
        assertEquals(dragon, manager.getCollection().getItems().toArray()[0]);
        Mockito.reset(manager.getBaseMan());
        Mockito.when(manager.getBaseMan().addDragon(Mockito.any())).thenThrow(SQLException.class);
        assertThrows(ExitException.class, () -> manager.seekCommand(addReq));
    }

    @Test
    void addIfMin() throws SQLException {
        Request addReq = new Request(CommandType.ADD_IF_MIN, null, dragon, admin);
        Mockito.when(manager.getBaseMan().addDragon(Mockito.any())).thenThrow(SQLException.class);
        assertThrows(ExitException.class, () -> manager.seekCommand(addReq));
        Mockito.reset(manager.getBaseMan());
        Mockito.when(manager.getBaseMan().addDragon(Mockito.any())).thenReturn(1);
        assertEquals("New dragon successfully added in the collection", manager.seekCommand(addReq));
        assertEquals(dragon, manager.getCollection().getItems().toArray()[0]);
        assertEquals("Object is not minimal", manager.seekCommand(addReq));
    }

    @Test
    void averageOfWeight() {
        manager.getCollection().add(dragon);
        Request averageReq = new Request(CommandType.AVERAGE_OF_WEIGHT, null, null, admin);
        assertEquals("Average weight = 100", manager.seekCommand(averageReq));
    }

    @Test
    void changePasswd() throws SQLException {
        Request changePasswd = new Request(CommandType.CHANGE_PASSWORD, "passwd", null, admin);
        manager.seekCommand(changePasswd);
        Mockito.doThrow(SQLException.class).when(manager.getBaseMan()).changePasswd(Mockito.any(), Mockito.any());
        assertThrows(ExitException.class, () -> manager.seekCommand(changePasswd));
    }

    @Test
    void clear() throws SQLException {
        manager.getCollection().add(dragon);
        assertEquals(1, manager.getCollection().getItems().size());
        Request clearReq = new Request(CommandType.CLEAR, null, null, admin);
        manager.seekCommand(clearReq);
        assertEquals(0, manager.getCollection().getItems().size());
        Mockito.doThrow(SQLException.class).when(manager.getBaseMan()).clearDragons(Mockito.any());
        assertThrows(ExitException.class, () -> manager.seekCommand(clearReq));
    }

    @Test
    void filterLessThanWeight() {
        Request filterReq = new Request(CommandType.FILTER_LESS_THAN_WEIGHT, 1000L, null, admin);
        assertEquals("No such elements in collection", manager.seekCommand(filterReq));
        manager.getCollection().add(dragon);
        assertNotEquals("No such elements in collection", manager.seekCommand(filterReq));
    }

    @Test
    void find() {
        Request findReq = new Request(CommandType.FIND, 1, null, admin);
        assertEquals("No such element in collection", manager.seekCommand(findReq));
        manager.getCollection().add(dragon);
        assertEquals(dragon.toString(), manager.seekCommand(findReq));
        findReq = new Request(CommandType.FIND, 123, null, admin);
        assertEquals("No such element in collection", manager.seekCommand(findReq));
    }

    @Test
    void help() {
        Request helpReq = new Request(CommandType.HELP, null, null, admin);
        assertNotNull(manager.seekCommand(helpReq));
        assertNotEquals("", manager.seekCommand(helpReq));
    }

    @Test
    void info() {
        Request infoReq = new Request(CommandType.INFO, null, null, admin);
        assertNotNull(manager.seekCommand(infoReq));
        assertNotEquals("", manager.seekCommand(infoReq));
    }

    @Test
    void minByAge() {
        Request minReq = new Request(CommandType.MIN_BY_AGE, null, null, admin);
        assertEquals("Collection is empty", manager.seekCommand(minReq));
        manager.getCollection().add(dragon);
        assertEquals(dragon.toString(), manager.seekCommand(minReq));
    }

    @Test
    void ping() {
        Request pingReq = new Request(CommandType.PING, null, null, admin);
        assertTrue(manager.seekCommand(pingReq).startsWith("Connected to server"));
    }

    @Test
    void removeById() throws SQLException, PermissionDeniedException {
        Request removeReq = new Request(CommandType.REMOVE_BY_ID, 1, null, admin);
        assertEquals("No such dragon in collection", manager.seekCommand(removeReq));
        manager.getCollection().add(dragon);
        assertEquals("Dragon was successfully removed", manager.seekCommand(removeReq));
        manager.getCollection().add(dragon);
        Mockito.doThrow(new PermissionDeniedException()).when(manager.getBaseMan()).removeById(1, admin);
        assertEquals("It is not your dragon! Permission denied", manager.seekCommand(removeReq));
        Mockito.doThrow(SQLException.class).when(manager.getBaseMan()).removeById(1, admin);
        assertThrows(ExitException.class, () -> manager.seekCommand(removeReq));
    }

    @Test
    void removeGreater() throws SQLException {
        Dragon dragon1 = new Dragon("aaa", new Coordinates(0, 0), 100, Color.RED, DragonCharacter.WISE,
                new DragonHead(0), admin);
        Request removeReq = new Request(CommandType.REMOVE_GREATER, null, dragon, admin);
        manager.getCollection().add(dragon);
        assertEquals("No such elements in collection", manager.seekCommand(removeReq));
        Request anotherRemove = new Request(CommandType.REMOVE_GREATER, null, dragon1, admin);
        assertEquals("Removed dragon test with id: 1", manager.seekCommand(anotherRemove));
        manager.getCollection().add(dragon);
        Mockito.doThrow(SQLException.class).when(manager.getBaseMan()).removeDragon(Mockito.any(), Mockito.any());
        assertThrows(ExitException.class, () -> manager.seekCommand(anotherRemove));
    }

    @Test
    void removeLower() throws SQLException {
        Dragon dragon1 = new Dragon("aaa", new Coordinates(0, 0), 100, Color.RED, DragonCharacter.WISE,
                new DragonHead(0), admin);
        Request removeReq = new Request(CommandType.REMOVE_LOWER, null, dragon1, admin);
        manager.getCollection().add(dragon1);
        assertEquals("No such elements in collection", manager.seekCommand(removeReq));
        Request anotherRemove = new Request(CommandType.REMOVE_LOWER, null, dragon, admin);
        assertEquals("Removed dragon aaa with id: 1", manager.seekCommand(anotherRemove));
        manager.getCollection().add(dragon1);
        Mockito.doThrow(SQLException.class).when(manager.getBaseMan()).removeDragon(Mockito.any(), Mockito.any());
        assertThrows(ExitException.class, () -> manager.seekCommand(anotherRemove));
    }

    @Test
    void show() {
        Request showReq = new Request(CommandType.SHOW, null, null, admin);
        assertEquals("Collection is empty", manager.seekCommand(showReq));
        manager.getCollection().add(dragon);
        assertEquals(dragon.toString(), manager.seekCommand(showReq));
    }

    @Test
    void signIn() throws SQLException, WrongPasswordException, NoSuchUserException {
        Request signInReq = new Request(CommandType.SIGN_IN, null, null, admin);
        //Mockito.doReturn(true).when(manager.getBaseMan()).signInUser(Mockito.any());
        assertEquals("User was signed in", manager.seekCommand(signInReq));
        //Mockito.doReturn(false).when(manager.getBaseMan()).signInUser(Mockito.any());
        //assertEquals("This user already working", manager.seekCommand(signInReq));
        Mockito.doThrow(SQLException.class).when(manager.getBaseMan()).signInUser(Mockito.any());
        assertThrows(ExitException.class, () -> manager.seekCommand(signInReq));
        Mockito.doThrow(WrongPasswordException.class).when(manager.getBaseMan()).signInUser(Mockito.any());
        assertNull(manager.seekCommand(signInReq));
        Mockito.doThrow(NoSuchUserException.class).when(manager.getBaseMan()).signInUser(Mockito.any());
        assertNull(manager.seekCommand(signInReq));
    }

    @Test
    void signUp() throws SQLException, LoginCollisionException {
        Request signInReq = new Request(CommandType.SIGN_UP, null, null, admin);
        assertEquals("User was signed up", manager.seekCommand(signInReq));
        Mockito.doThrow(SQLException.class).when(manager.getBaseMan()).addUser(Mockito.any());
        assertThrows(ExitException.class, () -> manager.seekCommand(signInReq));
        Mockito.doThrow(LoginCollisionException.class).when(manager.getBaseMan()).addUser(Mockito.any());
        assertNull(manager.seekCommand(signInReq));
    }

    @Test
    void update() throws SQLException, PermissionDeniedException {
        Request updateReq = new Request(CommandType.UPDATE, 1, dragon, admin);
        manager.getCollection().add(dragon);
        assertEquals("Dragon was updated", manager.seekCommand(updateReq));
        Mockito.doThrow(SQLException.class).when(manager.getBaseMan()).updateDragon(1, dragon, admin);
        assertThrows(ExitException.class, () -> manager.seekCommand(updateReq));
        Mockito.doThrow(new PermissionDeniedException()).when(manager.getBaseMan()).updateDragon(1, dragon, admin);
        assertEquals("It is not your dragon! Permission denied", manager.seekCommand(updateReq));
    }
}