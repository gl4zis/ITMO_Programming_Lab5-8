package org.application.lab5.exceptions;

public class IncorrectInputException extends IncorrectDataException {

    public IncorrectInputException(String message) {
        super("Данныe введены некорректно: " + message);
    }
}
