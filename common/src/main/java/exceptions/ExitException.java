package exceptions;

/**
 * Replaces System.exit(). Signals about exiting from app
 */
public class ExitException extends RuntimeException {
    public ExitException() {
        super("Bye bye");
    }
}