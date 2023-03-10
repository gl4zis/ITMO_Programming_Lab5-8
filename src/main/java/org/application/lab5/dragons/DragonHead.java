package org.application.lab5.dragons;

import org.application.lab5.exceptions.IncorrectDataException;

public class DragonHead {
    private final Float eyesCount; //Поле не может быть null

    public DragonHead(float eyesCount) throws IncorrectDataException {
        if (eyesCount == Double.POSITIVE_INFINITY) this.eyesCount = Float.MAX_VALUE;
        else if (eyesCount == Double.NEGATIVE_INFINITY) this.eyesCount = Float.MIN_VALUE;
        else this.eyesCount = eyesCount;
    }

    public float getEyesCount() {
        return eyesCount;
    }

    public String toString() {
        return "На голове " + eyesCount + " глаза";
    }
}
