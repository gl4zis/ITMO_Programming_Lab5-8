package main.exceptions;

public class IncorrectFileDataException extends RuntimeException {
    public IncorrectFileDataException() {
        super("Некорректные данные в файле");
    }

    public IncorrectFileDataException(String message) {
        super("Некорректные данные в файле: " + message);
    }
}