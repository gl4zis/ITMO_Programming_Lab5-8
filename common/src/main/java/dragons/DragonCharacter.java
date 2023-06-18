package dragons;

import java.io.Serializable;

/**
 * Character of dragon.
 * Can be one of three variants
 */
public enum DragonCharacter implements Serializable {
    WISE,
    CHAOTIC_EVIL,
    FICKLE;

    public static DragonCharacter getByName(String name) {
        for (DragonCharacter character : DragonCharacter.values()) {
            if (character.name().equals(name))
                return character;
        }
        return null;
    }
}
