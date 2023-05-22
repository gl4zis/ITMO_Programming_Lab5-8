package settings;

import general.MyLocales;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.junit.StrictStubsRunnerTestListener;
import user.User;

import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
        settings.saveUser();
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

    @Test
    void localize() {
        settings.setLocale(MyLocales.ENGLISH);
        String output = settings.localize("CHAOTIC_EVIL");
        assertEquals("Evil", output);
        settings.setLocale(MyLocales.RUSSIAN);
        output = settings.localize("CHAOTIC_EVIL");
        assertEquals("Злой", output);
    }
}