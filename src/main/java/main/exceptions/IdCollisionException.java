package main.exceptions;

public class IdCollisionException extends RuntimeException {
    public IdCollisionException() {
        super("У объектов одинаковый Id");
    }
}
