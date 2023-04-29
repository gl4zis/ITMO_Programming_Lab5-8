package exceptions;

public class LoginCollisionException extends Exception {
    public LoginCollisionException() {
        super("User with this login already exists");
    }
}
