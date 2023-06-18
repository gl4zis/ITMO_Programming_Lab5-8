package dragons;

import java.io.Serializable;

/**
 * Color of dragon.
 * Can be one of four variants
 */
public enum Color implements Serializable {
    GREEN,
    RED,
    ORANGE,
    BROWN;

    public static Color getByName(String name) {
        for (Color color : Color.values()) {
            if (color.name().equals(name))
                return color;
        }
        return null;
    }
}
