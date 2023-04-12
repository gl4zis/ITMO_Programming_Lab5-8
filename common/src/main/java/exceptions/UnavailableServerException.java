package exceptions;

/**
 * Signals, what server isn't reply
 */
public class UnavailableServerException extends Exception {

    /**
     * Standard constructor
     */
    public UnavailableServerException() {
        super("Server is unavailable. Reply will be shown, when server starts reply");
    }
}
