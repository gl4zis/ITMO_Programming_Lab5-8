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
    private MyLocales locale;
    private Properties settings;
    private String settingsPath;

    public Settings() {
        load();
    }

    private void load() {
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
        user = User.signIn(login, password);
    }

    public User getUser() {
        return user;
    }

    /**
     * Sets current user on the client.
     */
    public void setUser(User user) {
        this.user = user;
    }

    private void loadLocale() {
        String loc = (String) settings.get("locale");
        switch (loc.toUpperCase()) {
            case "EN" -> locale = MyLocales.ENGLISH;
            case "RU" -> locale = MyLocales.RUSSIAN;
        }
    }

    private void save() {
        String out = propsFormat(settings);
        try (FileWriter writer = new FileWriter(settingsPath, StandardCharsets.UTF_8)) {
            writer.write(out);
            writer.flush();
        } catch (IOException e) {
            LOGGER.error("Can't save settings");
        }
    }

    private String propsFormat(Properties properties) {
        StringBuilder strBuild = new StringBuilder();
        for (Object obj : properties.keySet()) {
            strBuild.append(obj).append('=');
            Object value = properties.get(obj);
            if (value != null)
                strBuild.append(value);
            strBuild.append('\n');
        }
        return strBuild.toString();
    }
}
