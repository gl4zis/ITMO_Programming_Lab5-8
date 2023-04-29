package exceptions;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super("You entered wrong password");
    }
}
