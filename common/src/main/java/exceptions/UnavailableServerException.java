package exceptions;

public class UnavailableServerException extends Exception {
    public UnavailableServerException() {
        super("Server is unavailable. Repeat request later");
    }
}
