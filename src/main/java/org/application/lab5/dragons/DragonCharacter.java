package org.application.lab5.dragons;

public enum DragonCharacter {
    WISE("Рассудительный"),
    CHAOTIC_EVIL("Очень злой"),
    FICKLE("Капризный");

    private final String name;

    DragonCharacter(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
