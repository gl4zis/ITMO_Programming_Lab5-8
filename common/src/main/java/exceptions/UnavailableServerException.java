package exceptions;

/**
 * Signals, what server isn't reply
 */
public class UnavailableServerException extends Exception {

    /**
     * Standard constructor
     */
    public UnavailableServerException() {
        super("Server is unavailable");
    }

    /**
     * Variate constructor
     */
    public UnavailableServerException(String message) {
        super("Server is unavailable. " + message);
    }
}
