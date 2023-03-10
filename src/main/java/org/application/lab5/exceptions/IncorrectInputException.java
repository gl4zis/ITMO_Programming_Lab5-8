package org.application.lab5.exceptions;

public class IncorrectInputException extends Exception {

    public IncorrectInputException() {
        super("Данныe введены некорректно");
    }

    public IncorrectInputException(String message) {
        super("Данныe введены некорректно: " + message);
    }
}