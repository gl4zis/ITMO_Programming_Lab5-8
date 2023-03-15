package org.application.lab5.dragons;

/**
 * Color of dragon.
 * Can be one of Four variants
 */
public enum Color {
    GREEN("Зеленый"),
    RED("Красный"),
    ORANGE("Оранжевый"),
    BROWN("Коричневый");

    private final String name;

    /**
     * Constructor sets name of color
     *
     * @param name of this color
     */
    Color(String name) {
        this.name = name;
    }

    /**
     * Returns name of color
     *
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }
}
