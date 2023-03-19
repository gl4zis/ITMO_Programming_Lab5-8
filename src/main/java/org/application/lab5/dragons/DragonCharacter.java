package org.application.lab5.dragons;

/**
 * Character of dragon.
 * Can be one of three variants
 */
public enum DragonCharacter {
    WISE("Wise"),
    CHAOTIC_EVIL("Evil"),
    FICKLE("Fickle");

    private final String name;

    /** Constructor sets name of character
     * @param name of this character
     */
    DragonCharacter(String name) {
        this.name = name;
    }

    /** Returns name of character
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }
}
