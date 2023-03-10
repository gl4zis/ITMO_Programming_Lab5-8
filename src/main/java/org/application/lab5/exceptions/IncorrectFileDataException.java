package org.application.lab5.exceptions;

public class IncorrectFileDataException extends IncorrectDataException {

    public IncorrectFileDataException(String message) {
        super("Некорректные данные в файле: " + message);
    }
}