package dragons;

import java.io.Serializable;

/**
 * Color of dragon.
 * Can be one of four variants
 */
public enum Color implements Serializable {
    GREEN("Green"),
    RED("Red"),
    ORANGE("Orange"),
    BROWN("Brown");

    private final String name;

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
