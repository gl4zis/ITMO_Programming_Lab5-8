package exceptions;

/**
 * Signals about wrong-written JSON file
 */
public class JsonParseException extends Exception {
    /**
     * Universal constructor
     */
    public JsonParseException(String message) {
        super(message);
    }
}
