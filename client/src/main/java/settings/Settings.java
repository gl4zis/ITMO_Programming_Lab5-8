package settings;

import client.ClientConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user.User;

import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Properties;

public class Settings {
    private static final Logger LOGGER = LogManager.getLogger(Settings.class);
    private User user;
    private boolean saveUser;
    private boolean darkTheme;
    private MyLocale locale;
    private String settingsPath;
    private ClientConnection connection;
    private HashMap<String, Color> colors;
    private int port;
    private String hostName;
    private static final String DARK_STYLE_PATH = "/styles/dark.properties";
    private static final String LIGH_STYLE_PATH = "/styles/light.properties";

    public Settings() {
        load();
    }

    private void load() {
        setDefault();
        colors = new HashMap<>();
        Properties settings = new Properties();
        settingsPath = this.getClass().getResource("/settings.cfg").getPath();
        if (settingsPath.contains("!")) {
            settingsPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            settingsPath = settingsPath.substring(0, settingsPath.lastIndexOf('/'));
            settingsPath += "settings.cfg";
        }
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(settingsPath), StandardCharsets.UTF_8);
            settings.load(is);
            loadServer(settings);
            loadUser(settings);
            loadLocale(settings);
            loadTheme(settings);
            loadStyle();
        } catch (IOException e) {
            LOGGER.error("Can't read settings file");
        }
    }

    private void setDefault() {
        locale = MyLocale.ENGLISH;
        saveUser = false;
        darkTheme = false;
    }

    private void loadServer(Properties settings) throws UnknownHostException {
        port = Integer.parseInt((String) settings.get("port"));
        hostName = (String) settings.get("host");
        connection = new ClientConnection(InetAddress.getByName(hostName), port);
    }

    private void loadUser(Properties settings) {
        String login = (String) settings.get("user");
        String password = (String) settings.get("password");
        if (!login.trim().isEmpty() && !password.trim().isEmpty()) {
            User newUser = User.signIn(login, password);
            if (connection.signIn(newUser)) {
                user = newUser;
                saveUser = true;
            }
        }
    }

    private void loadLocale(Properties settings) {
        String loc = (String) settings.get("locale");
        locale = MyLocale.getByName(loc);
    }

    private void loadTheme(Properties settings) {
        String theme = (String) settings.get("darkTheme");
        darkTheme = theme.equalsIgnoreCase("true");
    }

    private void loadStyle() throws IOException {
        String stylePath;
        if (darkTheme)
            stylePath = DARK_STYLE_PATH;
        else
            stylePath = LIGH_STYLE_PATH;
        Properties style = new Properties();
        InputStream is = Settings.class.getResourceAsStream(stylePath);
        style.load(is);
        for (Object str : style.keySet()) {
            colors.put((String) str, parseColor(style.get(str)));
        }
    }

    private Color parseColor(Object obj) {
        String str = ((String) obj).substring(1);
        int red = Integer.parseInt(str.substring(0, 2), 16);
        int green = Integer.parseInt(str.substring(2, 4), 16);
        int blue = Integer.parseInt(str.substring(4, 6), 16);
        return new Color(red, green, blue);
    }

    public void changeTheme() {
        darkTheme = !darkTheme;
        try {
            loadStyle();
        } catch (IOException e) {
            LOGGER.error("Can't load styles " + e.getMessage());
        }
    }

    public HashMap<String, Color> getColors() {
        return colors;
    }

    public boolean isDark() {
        return darkTheme;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void saveUser(boolean save) {
        saveUser = save;
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
        out.append("port=").append(port).append('\n');
        out.append("host=").append(hostName).append('\n');
        out.append("locale=").append(locale.name()).append('\n');
        out.append("darkTheme=").append(darkTheme).append('\n');
        if (saveUser) {
            out.append("user=").append(user.getLogin()).append('\n');
            out.append("password=").append(user.getHashedPasswd()).append('\n');
        } else {
            out.append("user=\n");
            out.append("password=\n");
        }
        return out.toString();
    }

    public MyLocale getLocale() {
        return locale;
    }

    public void setLocale(MyLocale locale) {
        this.locale = locale;
    }

    public ClientConnection getConnection() {
        return connection;
    }
}