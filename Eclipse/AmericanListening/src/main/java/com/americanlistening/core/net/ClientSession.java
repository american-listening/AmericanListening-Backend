package com.americanlistening.core.net;

/**
 * A client session is a way of knowing what user is sending what. This class
 * should be used server-side only.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public interface ClientSession {

	/**
	 * Returns the session ID.
	 * 
	 * @return The session ID.
	 */
	public long sessionID();

	/**
	 * Frees the current session.
	 */
	public void freeSession();

	/**
	 * Returns the connection to the client.
	 * 
	 * @return The connection.
	 */
	public Connection getConnection();

	/**
	 * Adds an input callback to the client session.
	 * 
	 * @param callback
	 *            The callback.
	 */
	public void addInputCallback(InputCallback callback);
}
