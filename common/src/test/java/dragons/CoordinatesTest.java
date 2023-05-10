package dragons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {
    Coordinates OO;
    Coordinates coords;

    @BeforeEach
    void setUp() {
        OO = new Coordinates(0, 0);
        coords = new Coordinates(3, 4);
        assertEquals(3, coords.getX());
        assertEquals(4, coords.getY());
    }

    @Test
    void distant() {
        assertEquals(5, OO.distant(coords));
    }

    @Test
    void compareTo() {
        assertTrue(OO.compareTo(coords) < 0);
    }

    @Test
    void testToString() {
        assertNotNull(coords.toString());
        assertTrue(coords.toString().length() > 0);
    }
}