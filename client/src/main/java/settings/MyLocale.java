package settings;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum MyLocale {
    ENGLISH("EN", "English (USA)", new Locale("en", "US")),
    RUSSIAN("RU", "Русский", new Locale("ru", "RU")),
    ROMANIAN("RO", "Românesc", new Locale("ro", "RO")),
    GREEK("EL", "Ελληνική", new Locale("el", "GR"));

    private final String shortName;
    private final String localedName;
    private final Locale lang;

    MyLocale(String shortName, String localedName, Locale lang) {
        this.shortName = shortName;
        this.localedName = localedName;
        this.lang = lang;
    }

    public static MyLocale getByName(String name) {
        for (MyLocale locale : MyLocale.values()) {
            if (name.equals(locale.getShortName()) || name.equals(locale.getLocaledName()) || name.equals(locale.name())) {
                return locale;
            }
        }
        return null;
    }

    public String getResource(String res) {
        try {
            ResourceBundle rB = ResourceBundle.getBundle("l10n.GUI", getLang());
            return rB.getString(res);
        } catch (MissingResourceException e) {
            return res;
        }
    }

    public String getShortName() {
        return shortName;
    }

    public String getLocaledName() {
        return localedName;
    }

    public Locale getLang() {
        return lang;
    }
}