package org.application.lab5.dragons;

import org.application.lab5.exceptions.IncorrectDataException;

/**
 * Dragon coordinates
 * Have fractional X and Y params
 */
public class Coordinates {
    private final double x; //Значение поля должно быть больше -497
    private final Float y; //Поле не может быть null

    /**
     * Constructor sets both of coordinates params.
     * X should be more than -497.
     * Converts infinities to the max values of each type
     *
     * @param x double param, should be more than -497
     * @param y float param
     * @throws IncorrectDataException if incorrect params inputted
     */
    public Coordinates(double x, float y) throws IncorrectDataException {
        if (x + 497 < -0.1E20) throw new IncorrectDataException("Неверные поля объекта Dragon");
        if (x == Double.POSITIVE_INFINITY) this.x = Double.MAX_VALUE;
        else this.x = x;
        if (y == Double.POSITIVE_INFINITY) this.y = Float.MAX_VALUE;
        else if (y == Double.NEGATIVE_INFINITY) this.y = Float.MIN_VALUE;
        else this.y = y;
    }

    /**
     * Returns param X of this coordinates
     *
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * Returns param Y of this coordinates
     *
     * @return y
     */
    public float getY() {
        return y;
    }

    /**
     * Returns the string output with info about this coordinates
     *
     * @return output
     */
    @Override
    public String toString() {
        return "\n\tx: " + x + "\n\ty: " + y;
    }
}
