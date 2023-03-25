package org.application.lab5.dragons;

import org.application.lab5.exceptions.IncorrectDataException;

import static java.lang.Math.pow;

/**
 * Dragon coordinates
 * Have fractional X and Y params
 */
public class Coordinates implements Comparable<Coordinates> {
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
        if (x + 497 < -0.1E20) throw new IncorrectDataException("Incorrect coordinates");
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
     * Calculates distant between two coordinate points
     *
     * @param c - other point
     * @return distant between two points
     */
    public double distant(Coordinates c) {
        return Math.sqrt(pow(this.x - c.x, 2) + pow(this.y - c.y, 2));
    }

    /**
     * Compares two coordinate points by distant from (0, 0)
     *
     * @param c the object to be compared.
     */
    @Override
    public int compareTo(Coordinates c) {
        Coordinates OO = new Coordinates(0, 0);
        return (int) (this.distant(OO) - c.distant(OO));
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
