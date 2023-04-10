package exceptions;

public class EmptyInputException extends IncorrectInputException {
    public EmptyInputException() {
        super("Empty string in the input");
    }
}
