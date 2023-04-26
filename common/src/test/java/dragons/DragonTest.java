package dragons;

import exceptions.IncorrectDataException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DragonTest {

    Dragon dragon1;
    Dragon dragon2;

    @BeforeAll
    static void checkConstructors() {
        assertThrows(IncorrectDataException.class, () ->
                new Dragon(-1, "test", new Coordinates(0, 0), new Date(), 100, Color.RED, DragonCharacter.WISE, new DragonHead(1)));
        assertThrows(IncorrectDataException.class, () ->
                new Dragon(1, "test", new Coordinates(0, 0), null, 100, Color.RED, DragonCharacter.WISE, new DragonHead(1)));
        assertThrows(IncorrectDataException.class, () ->
                new Dragon(null, new Coordinates(0, 0), 100, Color.RED, DragonCharacter.WISE, new DragonHead(1)));
        assertThrows(IncorrectDataException.class, () ->
                new Dragon("test", null, 100, Color.RED, DragonCharacter.WISE, new DragonHead(1)));
        assertThrows(IncorrectDataException.class, () ->
                new Dragon("test", new Coordinates(0, 0), 100, null, DragonCharacter.WISE, new DragonHead(1)));
        assertThrows(IncorrectDataException.class, () ->
                new Dragon("test", new Coordinates(0, 0), 100, Color.RED, null, new DragonHead(1)));
        assertThrows(IncorrectDataException.class, () ->
                new Dragon("test", new Coordinates(0, 0), 100, Color.RED, DragonCharacter.WISE, null));
        assertThrows(IncorrectDataException.class, () ->
                new Dragon("", new Coordinates(0, 0), 100, Color.RED, DragonCharacter.WISE, new DragonHead(1)));
        assertThrows(IncorrectDataException.class, () ->
                new Dragon("test", new Coordinates(0, 0), -3, Color.RED, DragonCharacter.WISE, new DragonHead(1)));
    }

    @BeforeEach
    void setUp() {
        dragon1 = new Dragon(1, "test", new Coordinates(0, 0), new Date(), 100, Color.RED, DragonCharacter.WISE, new DragonHead(1));
        dragon2 = new Dragon(1, "test", new Coordinates(0, 0), new Date(), 100, Color.RED, DragonCharacter.WISE, new DragonHead(1));
    }

    @Test
    void update() {
        dragon2 = new Dragon("updated", new Coordinates(1, 1), 1000, Color.RED, DragonCharacter.FICKLE, new DragonHead(10));
        dragon1.update(dragon2);
        assertEquals(1, dragon1.hashCode());
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
    }

    @Test
    void setId() {
        dragon1.setId(100);
        assertEquals(100, dragon1.hashCode());
    }

    @Test
    void compareTo() {
        assertEquals(dragon1.compareTo(dragon2), 0);
        dragon1 = new Dragon(2, "test2", new Coordinates(0, 0), new Date(), 100, Color.RED, DragonCharacter.WISE, new DragonHead(1));
        assertNotEquals(dragon1.compareTo(dragon2), 0);
    }

    @Test
    void toJson() {
        JSONObject jsonDr = dragon1.toJson();
        Set<String> keys = new HashSet<>();
        keys.add("id");
        keys.add("name");
        keys.add("coordinates");
        keys.add("creationDate");
        keys.add("age");
        keys.add("weight");
        keys.add("color");
        keys.add("character");
        keys.add("head");
        assertEquals(keys, jsonDr.keySet());
    }
}