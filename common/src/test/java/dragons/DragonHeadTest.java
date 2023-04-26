package dragons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DragonHeadTest {

    DragonHead head1;
    DragonHead head2;

    @BeforeEach
    void setUp() {
        head1 = new DragonHead(Float.POSITIVE_INFINITY);
        head2 = new DragonHead(Float.MAX_VALUE);
    }

    @Test
    void compareTo() {
        assertEquals(0, head1.compareTo(head2));
    }

    @Test
    void testToString() {
        assertNotNull(head1.toString());
        assertTrue(head1.toString().length() > 0);
    }
}