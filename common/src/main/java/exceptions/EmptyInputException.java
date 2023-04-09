package exceptions;

public class EmptyInputException extends Exception {
    public EmptyInputException() {
        super("Empty string in the input");
    }
}
