package org.application.lab5.exceptions;

/**
 * Signals that matched several objects
 */
public class NonUniqueValueException extends Exception {
    /**
     * Constructor with standard message
     */
    public NonUniqueValueException() {
        super("There are several such values");
    }
}
