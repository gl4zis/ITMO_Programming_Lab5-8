package client;

import exceptions.IncorrectDataException;

public enum MyLocales {
    ENGLISH,
    RUSSIAN;

    public static MyLocales getByName(String name) {
        for (MyLocales locale : MyLocales.values()) {
            if (name.equals(locale.name())) {
                return locale;
            }
        }
        throw new IncorrectDataException("Incorrect locale name");
    }
}
