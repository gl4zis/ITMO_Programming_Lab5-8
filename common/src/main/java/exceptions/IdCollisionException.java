package exceptions;

/**
 * Signals try of put objects with same id in collection
 */
public class IdCollisionException extends RuntimeException {
    /**
     * Constructor with standard message for this exception
     */
    public IdCollisionException() {
        super("Dragons have same id");
    }
}
