package dragons;

import java.io.Serializable;

/**
 * Character of dragon.
 * Can be one of three variants
 */
public enum DragonCharacter implements Serializable {
    WISE("Wise"),
    CHAOTIC_EVIL("Evil"),
    FICKLE("Fickle");

    private final String name;

    DragonCharacter(String name) {
        this.name = name;
    }

    /**
     * Returns name of character
     *
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }
}
