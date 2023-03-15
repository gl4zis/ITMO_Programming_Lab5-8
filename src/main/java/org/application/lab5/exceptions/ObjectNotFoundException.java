package org.application.lab5.exceptions;

/**
 * Signals what there are no such object in collection
 */
public class ObjectNotFoundException extends Exception {
    /**
     * Constructor with standard message for this exception
     */
    public ObjectNotFoundException() {
        System.out.println("Объект не найден");
    }
}
