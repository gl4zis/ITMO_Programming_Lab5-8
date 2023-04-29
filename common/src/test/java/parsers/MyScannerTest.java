package parsers;

import dragons.Color;
import dragons.Coordinates;
import dragons.Dragon;
import dragons.DragonCharacter;
import exceptions.ExitException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.User;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class MyScannerTest {

    MyScanner scanner;
    InputStream systemIn = System.in;

    @BeforeEach
    void setUp() {
        System.setIn(systemIn);
        InputStream is = new ByteArrayInputStream("lol\nexit\nclear\n".getBytes());
        scanner = new MyScanner(is);
    }

    @Test
    void nextLine() {
        assertEquals("lol", scanner.nextLine());
        assertEquals("exit", scanner.nextLine());
        assertEquals("clear", scanner.nextLine());
        assertNull(scanner.nextLine());
        InputStream is = new ByteArrayInputStream("".getBytes());
        System.setIn(is);
        scanner = new MyScanner(System.in);
        assertThrows(ExitException.class, () -> scanner.nextLine());
    }

    @Test
    void checkConsole() {
        assertNull(scanner.checkConsole());
        InputStream is = new ByteArrayInputStream("lol\nexit\nclear\n".getBytes());
        System.setIn(is);
        scanner = new MyScanner(System.in);
        assertEquals("lol", scanner.checkConsole());
        assertNull(scanner.checkConsole());
    }

    @Test
    void readDragon() {
        InputStream is = new ByteArrayInputStream("    \nname\n-500\ndvk\n6\n\n5\n-10\nalj\n4\n\n-1\n3\n5\n\n2\n4\n\n1\n\n0.003\n0\n".getBytes());
        System.setIn(is);
        scanner = new MyScanner(System.in);
        Dragon dragon = scanner.readDragon(User.signUp("admin", "qwerty"));
        assertEquals("name", dragon.getName());
        assertEquals(new Coordinates(6, 5).compareTo(dragon.getCoordinates()), 0);
        assertEquals(4, dragon.getAge());
        assertEquals(3, dragon.getWeight());
        assertEquals(Color.RED, dragon.getColor());
        assertEquals(DragonCharacter.WISE, dragon.getDragonCharacter());
        assertEquals(0, dragon.getDragonHead().getEyesCount());

        System.setIn(systemIn);
        is = new ByteArrayInputStream("name\n6\n5\n\nsgs\n".getBytes());
        scanner = new MyScanner(is);
        assertNull(scanner.readDragon(User.signUp("admin", "qwerty")));
    }
}