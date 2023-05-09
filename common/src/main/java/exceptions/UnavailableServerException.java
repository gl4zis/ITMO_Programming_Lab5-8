package exceptions;

/**
 * Signals, what server isn't reply
 */
public class UnavailableServerException extends Exception {
    /**
     * Variate constructor
     */
    public UnavailableServerException(String message) {
        super("Server is unavailable. " + message);
    }
}
