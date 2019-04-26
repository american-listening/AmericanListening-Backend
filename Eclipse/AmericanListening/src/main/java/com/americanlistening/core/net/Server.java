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
}
