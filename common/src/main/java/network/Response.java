package network;

import java.io.Serializable;

/**
 * Record for sending response, includes only string message, from server to client
 *
 * @param message server reply
 */
public record Response(String message) implements Serializable {
}
