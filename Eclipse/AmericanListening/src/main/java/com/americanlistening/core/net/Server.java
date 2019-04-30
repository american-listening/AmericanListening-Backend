package com.americanlistening.core.net;

import java.io.IOException;

/**
 * The server interface signals that a class is capable of hosting a server to
 * accept client connections.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public interface Server {

	/**
	 * Initializes the server.
	 */
	public void init() throws IOException;

	/**
	 * Starts the server loop.
	 */
	public void dispatchServer();

	/**
	 * Stops the server from running.
	 * 
	 * @throws IOException When an I/O error occurs.
	 */
	public void stop() throws IOException;

	/**
	 * Adds an error callback to this server.
	 * 
	 * @param callback The callback.
	 */
	public void addErrorCallback(ErrorCallback callback);

	/**
	 * Adds a connection callback to this server.
	 * 
	 * @param callback The callback.
	 */
	public void addConnectionCallback(ConnectionCallback callback);

	/**
	 * Returns whether the server is encrypted.
	 * 
	 * @return <code>true</code> if the server is encrypted and <code>false</code>
	 *         otherwise.
	 */
	public boolean isEncrypted();

	/**
	 * Returns the value of a property.
	 * 
	 * @param key The key to set.
	 * @return The respective value.
	 */
	public Object getProperty(String key);

	/**
	 * Sets the property of key <code>key</code>.
	 * 
	 * @param key   The key to set.
	 * @param value The value to set.
	 */
	public void setProperty(String key, Object value);

	/**
	 * Returns whether the server has key <code>key</code>.
	 * 
	 * @param key The key to test for.
	 * @return <code>true</code> if the key is there, and <code>false</code>
	 *         otherwise.
	 */
	public boolean hasProperty(String key);
}
