package exceptions;

/**
 * Signals, what you have no rights for some action
 */
public class PermissionDeniedException extends Exception {
    /**
     * Constructor with standard message
     */
    public PermissionDeniedException() {
        super("Permission denied");
    }
}
