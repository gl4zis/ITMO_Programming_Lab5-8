package settings;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public abstract class Localization {

    public static String RULocalize(String message) {
        try {
            Properties RULocale = loadProp("ru_locale.properties");
            return localize(message, RULocale);
        } catch (IOException e) {
            return message;
        }
    }

    public static String ENLocalize(String message) {
        try {
            Properties ENLocale = loadProp("en_locale.properties");
            return localize(message, ENLocale);
        } catch (IOException e) {
            return message;
        }
    }

    private static Properties loadProp(String filename) throws IOException {
        InputStream path = Localization.class.getClassLoader().getResourceAsStream(filename);
        InputStreamReader isr = new InputStreamReader(path, StandardCharsets.UTF_8);
        Properties properties = new Properties();
        properties.load(isr);
        return properties;
    }

    private static String localize(String message, Properties locale) {
        for (Object obj : locale.keySet()) {
            message = message.replaceAll((String) obj, (String) locale.get(obj));
        }
        return message;
    }
}
