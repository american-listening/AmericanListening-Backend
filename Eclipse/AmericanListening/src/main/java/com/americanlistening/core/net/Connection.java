package com.americanlistening.core.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

/**
 * Interface representing a mutual connection over the Internet.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public interface Connection {

	/**
	 * Returns the input stream for this connection.
	 * 
	 * @return The input stream.
	 */
	public InputStream inputStream();

	/**
	 * Returns the output stream for this connection.
	 * 
	 * @return The output stream.
	 */
	public OutputStream outputStream();

	/**
	 * Starts the client's loop.
	 */
	public void start();

	/**
	 * Stops the connection and disconnections.
	 * 
	 * @throws IOExeption
	 *             When an I/O error occurs.
	 */
	public void stop() throws IOException;

	/**
	 * Forces any outstanding bytes in the connection's output stream to be written.
	 * This method is effectively equivalent to <code>outputStream().flush()</code>
	 * and serves as a convenience.
	 * 
	 * @throws IOException
	 *             When an I/O Error occurs.
	 */
	public default void push() throws IOException {
		outputStream().flush();
	}

	/**
	 * Closes the connection.
	 * 
	 * @throws IOException
	 *             When an I/O Error occurs.
	 */
	public void close() throws IOException;

	/**
	 * Returns whether the connection is actually connected to something.
	 * 
	 * @return <code>true</code> if it is connected and <code>false</code>
	 *         otherwise.
	 */
	public boolean isConnected();

	/**
	 * Returns whether the connection is secure.
	 * 
	 * @return <code>true</code> if it is a secure connection and <code>false</code>
	 *         otherwise.
	 */
	public boolean isSecure();

	/**
	 * Returns the address of this connection.
	 * 
	 * @return The address, or <code>null</code> if the object is not connected.
	 */
	public InetAddress getAddress();

	/**
	 * Returns the port of this connection.
	 * 
	 * @return The port, or <code>-1</code> if the object is not connected or the
	 *         connection is closed.
	 */
	public int getPort();

	/**
	 * Adds an input callback to this connection.
	 * 
	 * @param callback
	 *            The callback to add;
	 */
	public void addInputCallback(InputCallback callback);

	/**
	 * Adds an error callback to this connection.
	 * 
	 * @param callback
	 *            The callback to add.
	 */
	public void addErrorCallback(ErrorCallback callback);

	/**
	 * Returns the current client session.
	 * 
	 * @return The session to return. Returns <code>null</code> if the connection is
	 *         server-side.
	 */
	public ClientSession getSession();
}
