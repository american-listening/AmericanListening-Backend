package com.americanlistening.core.net;

/**
 * A connection callback is used to detect when a client has connection to a
 * server.
 * 
 * @author Ethan Vrhel
 *
 */
@FunctionalInterface
public interface ConnectionCallback {

	/**
	 * Called when an incoming connection has been accepted.
	 * 
	 * @param connection The accepted connection.
	 */
	public void onConnect(Connection connection);
}
