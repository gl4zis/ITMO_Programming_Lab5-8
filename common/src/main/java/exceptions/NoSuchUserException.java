package exceptions;

/**
 * Signals what user with this login is not exists
 */
public class NoSuchUserException extends Exception {
    /**
     * Constructor with standard message
     */
    public NoSuchUserException() {
        super("There are no user with this login in data base");
    }
}
