package client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user.User;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Settings {
    private static final Logger LOGGER = LogManager.getLogger(Settings.class);
    private User user;
    private boolean saveUser;
    private MyLocales locale;
    private Properties settings;
    private String settingsPath;

    public Settings() {
        load();
    }

    private void load() {
        saveUser = false;
        settings = new Properties();
        settingsPath = this.getClass().getClassLoader().getResource("settings.properties").getFile();
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(settingsPath), StandardCharsets.UTF_8);
            settings.load(is);
            loadUser();
            loadLocale();
        } catch (IOException e) {
            LOGGER.error("Can't read settings file");
        }
    }

    private void loadUser() {
        String login = (String) settings.get("user");
        String password = (String) settings.get("password");
        if (!login.trim().isEmpty() && !password.trim().isEmpty()) {
            user = User.signIn(login, password);
            saveUser();
        }
    }

    private void loadLocale() {
        String loc = (String) settings.get("locale");
        locale = MyLocales.getByName(loc);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void saveUser() {
        saveUser = true;
    }

    public void save() {
        String out = fileFormat();
        try (FileWriter writer = new FileWriter(settingsPath, StandardCharsets.UTF_8)) {
            writer.write(out);
            writer.flush();
        } catch (IOException e) {
            LOGGER.error("Can't save settings");
        }
    }

    private String fileFormat() {
        StringBuilder out = new StringBuilder();
        out.append("locale=").append(locale.name()).append('\n');
        if (saveUser) {
            out.append("user=").append(user.getLogin()).append('\n');
            out.append("password=").append(user.getHashedPasswd()).append('\n');
        } else {
            out.append("user=\n");
            out.append("password=\n");
        }
        return out.toString();
    }
}
