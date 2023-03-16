package org.application.lab5.exceptions;

/**
 * Signals try of assign forbidden values
 */
public class IncorrectDataException extends RuntimeException {
    /** Universal constructor
     * @param message outputs, when this exception throws
     */
    public IncorrectDataException(String message) {
        super(message);
    }
}
