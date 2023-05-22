package settings;

import general.MyLocales;
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
    private String settingsPath;

    public Settings() {
        load();
    }

    private void load() {
        saveUser = false;
        Properties settings = new Properties();
        settingsPath = this.getClass().getClassLoader().getResource("settings.cfg").getPath();
        if (settingsPath.contains("!")) {
            settingsPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            settingsPath = settingsPath.substring(0, settingsPath.length() - 16);
            settingsPath += "settings.cfg";
        }
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(settingsPath), StandardCharsets.UTF_8);
            settings.load(is);
            loadUser(settings);
            loadLocale(settings);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Can't read settings file");
            setDefault();
        }
    }

    private void setDefault() {
        locale = MyLocales.ENGLISH;
        saveUser = false;
    }

    private void loadUser(Properties settings) {
        String login = (String) settings.get("user");
        String password = (String) settings.get("password");
        if (!login.trim().isEmpty() && !password.trim().isEmpty()) {
            user = User.signIn(login, password);
            saveUser();
        }
    }

    private void loadLocale(Properties settings) {
        String loc = (String) settings.get("locale");
        locale = MyLocales.getByName(loc);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        saveUser = false;
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

    public void setLocale(MyLocales locale) {
        this.locale = locale;
    }

    public String localize(String message) {
        return switch (locale) {
            case ENGLISH -> Localization.ENLocalize(message);
            case RUSSIAN -> Localization.RULocalize(message);
        };
    }
}
