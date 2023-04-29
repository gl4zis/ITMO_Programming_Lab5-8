package network;

import commands.CommandType;
import dragons.Dragon;
import user.User;

import java.io.Serializable;

/**
 * Request from client to server.
 * Includes commandType, argument, dragon object
 */
public record Request(CommandType command, Object arg, Dragon dragon, User user) implements Serializable {
}
