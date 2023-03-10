package org.application.lab5.exceptions;

public class IdCollisionException extends RuntimeException {
    public IdCollisionException() {
        super("У объектов одинаковый Id");
    }
}
