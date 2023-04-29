package exceptions;

public class NoSuchUserException extends Exception {
    public NoSuchUserException() {
        super("There are no user with this login in data base");
    }
}
