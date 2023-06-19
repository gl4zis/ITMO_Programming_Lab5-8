package network;

import java.io.Serializable;

/**
 * Record for sending response, includes only string message, from server to client
 */
public class Response implements Serializable {
    public final String message;

    public Response(String message) {
        this.message = message;
    }
}