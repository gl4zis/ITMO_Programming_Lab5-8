package exceptions;

/**
 * Signals try of assign forbidden values in user input
 */
public class IncorrectInputException extends IncorrectDataException {
    /**
     * Universal constructor
     *
     * @param message outputs, when this exception throws
     */
    public IncorrectInputException(String message) {
        super("Incorrect data inputted: " + message);
    }
}
