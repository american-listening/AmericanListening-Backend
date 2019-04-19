package com.americanlistening.core.net;

/**
 * A client session is a way of knowing what user is sending what.
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
	 * Handles input from the remote client.
	 * 
	 * @param inp The input to process.
	 */
	public void handleInput(String inp);
}
