package dragons;

import exceptions.IncorrectDataException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.User;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DragonTest {

    private static User user;
    private Dragon dragon1;
    private Dragon dragon2;

    @BeforeAll
    static void checkConstructors() {
        user = User.signUp("admin", "qwerty");
        assertThrows(IncorrectDataException.class, () ->
                new Dragon(-1, "key", "test", new Coordinates(0, 0), new Date(), 100, Color.RED, DragonCharacter.WISE, new DragonHead(1), user));
        assertThrows(IncorrectDataException.class, () ->
                new Dragon(1, "key", "test", new Coordinates(0, 0), null, 100, Color.RED, DragonCharacter.WISE, new DragonHead(1), user));
        assertThrows(IncorrectDataException.class, () ->
                new Dragon(1, null, "test", new Coordinates(0, 0), new Date(), 100, Color.RED, DragonCharacter.WISE, new DragonHead(1), user));
        assertThrows(IncorrectDataException.class, () ->
                new Dragon(null, new Coordinates(0, 0), 100, Color.RED, DragonCharacter.WISE, new DragonHead(1), user));
        assertThrows(IncorrectDataException.class, () ->
                new Dragon("test", null, 100, Color.RED, DragonCharacter.WISE, new DragonHead(1), user));
        assertThrows(IncorrectDataException.class, () ->
                new Dragon("test", new Coordinates(0, 0), 100, null, DragonCharacter.WISE, new DragonHead(1), user));
        assertThrows(IncorrectDataException.class, () ->
                new Dragon("test", new Coordinates(0, 0), 100, Color.RED, null, new DragonHead(1), user));
        assertThrows(IncorrectDataException.class, () ->
                new Dragon("test", new Coordinates(0, 0), 100, Color.RED, DragonCharacter.WISE, null, user));
        assertThrows(IncorrectDataException.class, () ->
                new Dragon("", new Coordinates(0, 0), 100, Color.RED, DragonCharacter.WISE, new DragonHead(1), user));
        assertThrows(IncorrectDataException.class, () ->
                new Dragon("test", new Coordinates(0, 0), -3, Color.RED, DragonCharacter.WISE, new DragonHead(1), user));
        assertThrows(IncorrectDataException.class, () ->
                new Dragon("test", new Coordinates(0, 0), -3, Color.RED, DragonCharacter.WISE, new DragonHead(1), null));
    }

    @BeforeEach
    void setUp() {
        dragon1 = new Dragon(10, "key", "test", new Coordinates(0, 0), new Date(), 100, Color.RED, DragonCharacter.WISE, new DragonHead(1), user);
        dragon2 = new Dragon(10, "key", "test", new Coordinates(0, 0), new Date(0), 100, Color.RED, DragonCharacter.WISE, new DragonHead(1), user);
        assertEquals(user, dragon1.getCreator());
        assertEquals(new Date(0), dragon2.getCreationDate());
    }

    @Test
    void update() {
        dragon2 = new Dragon("updated", new Coordinates(1, 1), 1000, Color.RED, DragonCharacter.FICKLE, new DragonHead(10), user);
        dragon1.update(dragon2);
        assertEquals(10, dragon1.hashCode());
        assertNotEquals(dragon1, dragon2);
        dragon1.setId(dragon2.hashCode());
        assertEquals(dragon1, dragon2);
    }

    @Test
    void setAge() {
        assertThrows(IncorrectDataException.class, () -> dragon1.setAge(-100));
        dragon1.setAge(100);
        assertEquals(100, dragon1.getAge());
    }

    @Test
    void testToString() {
        assertNotNull(dragon1.toString());
        assertNotEquals(0, dragon1.toString().trim().length());
    }

    @Test
    void testEquals() {
        assertEquals(dragon1, dragon2);
        assertNotEquals("dragon2", dragon1);
        assertNotEquals(null, dragon2);
        dragon2.setId(100);
        assertNotEquals(dragon1, dragon2);
    }

    @Test
    void setId() {
        dragon1.setId(100);
        assertEquals(100, dragon1.hashCode());
    }

    @Test
    void compareTo() {
        assertEquals(dragon1.compareTo(dragon2), 0);
        dragon1 = new Dragon(2, "key", "test2", new Coordinates(0, 0), new Date(), 100, Color.RED, DragonCharacter.WISE, new DragonHead(1), user);
        assertNotEquals(dragon1.compareTo(dragon2), 0);


    }
}