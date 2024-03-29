package settings;

import GUI.MyFrame;
import client.ClientConnectionTCP;
import commands.CommandProcessor;
import commands.CommandType;
import dragons.Dragon;
import dragons.DragonCollection;
import exceptions.UnavailableServerException;
import network.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user.User;

import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Settings {
    private static final Logger LOGGER = LogManager.getLogger(Settings.class);
    private static final String DARK_STYLE_PATH = "/styles/dark.properties";
    private static final String LIGH_STYLE_PATH = "/styles/light.properties";
    private final CommandProcessor processor;
    private User user;
    private boolean saveUser;
    private boolean darkTheme;
    private MyLocale locale;
    private String settingsPath;
    private ClientConnectionTCP connection;
    private HashMap<String, Color> colors;
    private HashMap<String, Color> dark;
    private HashMap<String, Color> light;
    private int port;
    private String hostName;
    private MyFrame mainWindow;
    private boolean connected;
    private DragonCollection collection;

    public Settings() {
        saveUser = false;
        connected = true;
        processor = new CommandProcessor(this);
        load();
    }

    private void load() {
        setDefault();
        colors = new HashMap<>();
        Properties settings = new Properties();
        settingsPath = System.getProperty("user.home") + "/dragon_sett.cfg";
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(settingsPath), StandardCharsets.UTF_8);
            settings.load(is);
        } catch (IOException e) {
            LOGGER.error("Can't read settings file");
        }
        try {
            loadServer(settings);
        } catch (Exception e) {
            LOGGER.error("Can't read server settings");
        }
        try {
            loadUser(settings);
        } catch (Exception e) {
            LOGGER.error("Can't read user settings");
        }
        try {
            loadLocale(settings);
        } catch (Exception e) {
            LOGGER.error("Can't read locale settings");
        }
        try {
            loadTheme(settings);
        } catch (Exception e) {
            LOGGER.error("Can't read theme settings");
        }
        try {
            loadStyle();
        } catch (Exception e) {
            LOGGER.error("Can't read styles");
        }
    }

    private void setDefault() {
        locale = MyLocale.ENGLISH;
        Locale.setDefault(locale.getLang());
        saveUser = false;
        darkTheme = false;
        port = 9812;
        hostName = "localhost";
        try {
            connection = new ClientConnectionTCP(InetAddress.getByName(hostName), port);
        } catch (UnknownHostException ignored) {
        } //Can't be thrown
    }

    private void loadServer(Properties settings) throws UnknownHostException {
        port = Integer.parseInt((String) settings.get("port"));
        hostName = (String) settings.get("host");
        connection = new ClientConnectionTCP(InetAddress.getByName(hostName), port);
    }

    private void loadUser(Properties settings) {
        String login = (String) settings.get("user");
        String password = (String) settings.get("password");
        if (!login.trim().isEmpty() && !password.trim().isEmpty()) {
            User newUser = User.signIn(login, password);
            if (signIn(newUser))
                setUser(newUser);
        }
    }

    private void loadLocale(Properties settings) {
        String loc = (String) settings.get("locale");
        locale = MyLocale.getByName(loc);
        Locale.setDefault(locale.getLang());
    }

    private void loadTheme(Properties settings) {
        String theme = (String) settings.get("darkTheme");
        darkTheme = theme.equalsIgnoreCase("true");
    }

    private void loadStyle() throws IOException {
        light = new HashMap<>();
        Properties style = new Properties();
        InputStream is = this.getClass().getResourceAsStream(LIGH_STYLE_PATH);
        style.load(is);
        for (Object str : style.keySet()) {
            light.put((String) str, parseColor(style.get(str)));
        }
        dark = new HashMap<>();
        style = new Properties();
        is = this.getClass().getResourceAsStream(DARK_STYLE_PATH);
        style.load(is);
        for (Object str : style.keySet()) {
            dark.put((String) str, parseColor(style.get(str)));
        }
        if (darkTheme)
            colors = dark;
        else
            colors = light;
    }

    private Color parseColor(Object obj) {
        String str = ((String) obj).substring(1);
        int red = Integer.parseInt(str.substring(0, 2), 16);
        int green = Integer.parseInt(str.substring(2, 4), 16);
        int blue = Integer.parseInt(str.substring(4, 6), 16);
        return new Color(red, green, blue);
    }

    public void changeTheme() {
        if (darkTheme)
            colors = light;
        else
            colors = dark;
        darkTheme = !darkTheme;
    }

    public HashMap<String, Color> getColors() {
        return colors;
    }

    public HashMap<String, Color> getDarkColors() {
        return dark;
    }

    public HashMap<String, Color> getLightColors() {
        return light;
    }

    public boolean isDark() {
        return darkTheme;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        saveUser = user != null;
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

    public void loadCollection() {
        try {
            DragonCollection newCollection = connection.getCollection();
            if (newCollection != null)
                collection = newCollection;
            connected = true;
        } catch (UnavailableServerException e) {
            connected = false;
        }
        if (mainWindow != null)
            mainWindow.checkConnect();
        if (!connected)
            connection.reconnect();
    }

    public String getResponse(Request request) {
        String reply = null;
        try {
            reply = connection.getReply(request);
            connected = true;
        } catch (UnavailableServerException e) {
            connected = false;
        }
        if (mainWindow != null)
            mainWindow.checkConnect();
        if (!connected)
            connection.reconnect();
        return reply;
    }

    public void runGUI() {
        mainWindow = new MyFrame("Dragon application", this);
    }

    public MyFrame getMainWindow() {
        return mainWindow;
    }

    public CommandProcessor getProcessor() {
        return processor;
    }

    public MyLocale getLocale() {
        return locale;
    }

    public void setLocale(MyLocale locale) {
        this.locale = locale;
        Locale.setDefault(locale.getLang());
    }

    public boolean isConnected() {
        return connected;
    }

    public Collection<Dragon> getCollection() {
        if (collection == null)
            return new HashSet<>();
        else
            return collection.getItems();
    }

    public boolean signIn(User user) {
        Request request = new Request(CommandType.SIGN_IN, null, null, user);
        String reply = getResponse(request);
        if (reply == null)
            return false;
        else
            return reply.startsWith("User was");
    }

    public boolean signUp(User user) {
        Request request = new Request(CommandType.SIGN_UP, null, null, user);
        String reply = getResponse(request);
        if (reply == null)
            return false;
        else
            return reply.startsWith("User was");
    }

    public boolean changePasswd(User user, String newPasswd) {
        Request request = new Request(CommandType.CHANGE_PASSWORD, newPasswd, null, user);
        String reply = getResponse(request);
        if (reply == null)
            return false;
        else
            return reply.startsWith("Password was");
    }
}