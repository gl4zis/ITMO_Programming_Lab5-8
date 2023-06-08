package user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    User admin = User.signUp("admin", "qwerty");

    @BeforeAll
    static void setIn() {
        InputStream is = new ByteArrayInputStream(":!?//\nadmin\nqwerty\n".getBytes());
        System.setIn(is);
    }

    @Test
    void signIn() {
        User user = User.signIn("admin", admin.getHashedPasswd());
        assertEquals(admin, user);
    }

    @Test
    void getMD5Hash() {
        assertEquals("d8578edf8458ce06fbc5bb76a58c5ca4", User.getMD5Hash("qwerty"));
    }

    @Test
    void getLogin() {
        assertEquals("admin", admin.getLogin());
        assertEquals(admin.getLogin(), admin.toString());
    }
}