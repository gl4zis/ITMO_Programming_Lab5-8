package exceptions;

/**
 * Signals, what user inputted wrong password
 */
public class WrongPasswordException extends Exception {
    /**
     * Constructor with standard message
     */
    public WrongPasswordException() {
        super("You entered wrong password");
    }
}
