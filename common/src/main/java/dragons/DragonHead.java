package dragons;

import java.io.Serializable;

/**
 * Head of dragon.
 * Have eyes
 */
public class DragonHead implements Comparable<DragonHead>, Serializable {
    private final Float eyesCount; //Поле не может быть null

    /**
     * Constructor sets count of eyes.
     * Converts infinity to the max value
     *
     * @param eyesCount count of eyes
     */
    public DragonHead(float eyesCount) {
        if (eyesCount == Float.POSITIVE_INFINITY) this.eyesCount = Float.MAX_VALUE;
        else if (eyesCount == Float.NEGATIVE_INFINITY) this.eyesCount = Float.MIN_VALUE;
        else this.eyesCount = eyesCount;
    }

    /**
     * Returns count of eyes
     *
     * @return count of eyes
     */
    public float getEyesCount() {
        return eyesCount;
    }

    /**
     * Compares to heads by count of eyes
     *
     * @param head the object to be compared.
     */
    @Override
    public int compareTo(DragonHead head) {
        return (int) (this.eyesCount - head.eyesCount);
    }

    /**
     * Returns string output with information about this head
     *
     * @return output
     */
    @Override
    public String toString() {
        return "Has " + eyesCount.intValue() + " eyes";
    }
}
