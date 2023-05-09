package network;

import java.io.Serializable;

/**
 * Record for sending response, includes only string message, from server to client
 */
public record Response(String message) implements Serializable {
}
