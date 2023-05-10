package collection;

import dragons.*;
import exceptions.IdCollisionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.User;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DragonCollectionTest {

    DragonCollection collection;
    Dragon dragon;


    @BeforeEach
    void init() {
        collection = new DragonCollection();
        dragon = new Dragon(1, "name", new Coordinates(6, 5), new Date(), 3, Color.RED, DragonCharacter.WISE, new DragonHead(0), User.signUp("admin", "qwerty"));
    }

    @Test
    void add() {
        collection.add(dragon);
        assertEquals(1, collection.getItems().size());
        assertThrows(IdCollisionException.class, () -> collection.add(dragon));
    }

    @Test
    void remove() {
        collection.add(dragon);
        assertThrows(NullPointerException.class, () -> collection.remove(null));
        collection.remove(dragon);
        assertEquals(0, collection.getItems().size());
    }

    @Test
    void find() {
        assertNull(collection.find(1));
        collection.add(dragon);
        assertNotNull(collection.find(1));
    }

    @Test
    void getMaxId() {
        assertEquals(0, collection.getMaxId());
        collection.add(dragon);
        assertEquals(1, collection.getMaxId());
    }

    @Test
    void clear() {
        collection.add(dragon);
        assertTrue(collection.checkId(1));
        assertFalse(collection.checkId(2));
        collection.clear(User.signUp("admin", "qwerty"));
        assertEquals(0, collection.getItems().size());
    }

    @Test
    void getAverageWeight() {
        assertEquals(0, collection.getAverageWeight());
        collection.add(dragon);
        assertEquals(3, collection.getAverageWeight());
    }

    @Test
    void minByAge() {
        assertNull(collection.minByAge());
        collection.add(dragon);
        assertEquals(dragon, collection.minByAge());
    }

    @Test
    void getMinDragon() {
        assertNull(collection.minByAge());
        collection.add(dragon);
        assertEquals(dragon, collection.getMinDragon());
    }

    @Test
    void testToString() {
        assertNotNull(collection.toString());
        assertTrue(collection.toString().length() > 0);
    }
}