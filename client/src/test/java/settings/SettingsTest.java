package settings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.User;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SettingsTest {

    private Settings settings;

    @BeforeEach
    void init() {
        settings = new Settings();
    }

    @Test
    void setUser() {
        User user = User.signUp("admin", "qwerty");
        settings.setUser(user);
        assertEquals(user, settings.getUser());
    }

    @Test
    void saveUser() throws Exception {
        settings.saveUser(true);
        Field saveUser = settings.getClass().getDeclaredField("saveUser");
        saveUser.setAccessible(true);
        boolean save = saveUser.getBoolean(settings);
        assertTrue(save);
        settings.setUser(User.signUp("admin", "qwerty"));
        settings.save();
        new Settings();
    }

    @Test
    void save() throws Exception {
        settings.save();
        Field saveUser = settings.getClass().getDeclaredField("saveUser");
        saveUser.setAccessible(true);
        saveUser.setBoolean(settings, false);
        settings.save();
    }
}