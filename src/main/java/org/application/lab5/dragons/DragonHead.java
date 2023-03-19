package org.application.lab5.dragons;

import org.application.lab5.exceptions.IncorrectDataException;

/**
 * Head of dragon.
 * Have eyes
 */
public class DragonHead {
    private final Float eyesCount; //Поле не может быть null

    /** Constructor sets count of eyes.
     * Converts infinity to the max value
     * @param eyesCount count of eyes
     */
    public DragonHead(float eyesCount) {
        if (eyesCount == Double.POSITIVE_INFINITY) this.eyesCount = Float.MAX_VALUE;
        else if (eyesCount == Double.NEGATIVE_INFINITY) this.eyesCount = Float.MIN_VALUE;
        else this.eyesCount = eyesCount;
    }

    /** Returns count of eyes
     * @return count of eyes
     */
    public float getEyesCount() {
        return eyesCount;
    }

    /** Returns string output with information about this head
     * @return output
     */
    @Override
    public String toString() {
        return "Has " + eyesCount.intValue() + " eyes";
    }
}
