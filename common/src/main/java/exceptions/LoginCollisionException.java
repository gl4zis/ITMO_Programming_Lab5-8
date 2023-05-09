package exceptions;

/**
 * Signals about trying signing up two users with same login
 */
public class LoginCollisionException extends Exception {
    /**
     * Constructor with standard message
     */
    public LoginCollisionException() {
        super("User with this login already exists");
    }
}
