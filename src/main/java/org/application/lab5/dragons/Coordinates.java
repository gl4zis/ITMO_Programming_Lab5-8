package org.application.lab5.dragons;

import org.application.lab5.exceptions.IncorrectFileDataException;

public class Coordinates {
    private final double x; //Значение поля должно быть больше -497
    private final Float y; //Поле не может быть null

    public Coordinates(double x, float y) throws IncorrectFileDataException {
        if (x + 497 < -0.1E20) throw new IncorrectFileDataException("Неверные поля объекта Dragon");
        if (x == Double.POSITIVE_INFINITY) this.x = Double.MAX_VALUE;
        else this.x = x;
        if (y == Double.POSITIVE_INFINITY) this.y = Float.MAX_VALUE;
        else if (y == Double.NEGATIVE_INFINITY) this.y = Float.MIN_VALUE;
        else this.y = y;
    }

    public double getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "\n\tx: " + x + "\n\ty: " + y;
    }
}
