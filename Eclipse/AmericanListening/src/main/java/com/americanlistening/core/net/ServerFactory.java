package com.americanlistening.core.net;

import java.io.IOException;

/**
 * Factory that handles the creation of servers.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public abstract class ServerFactory {

	public static final ServerFactory sslServerFactory = new SSLServerFactory();

	private static class SSLServerFactory extends ServerFactory {

		@Override
		public Server createServer(Sessions sessions, int port) throws IOException {
			return new SSLServer(sessions, port);
		}
	}

	/**
	 * Creates a server on port <code>port</code>.
	 * 
	 * @param sessions The client sessions handle to use on the server.
	 * @param port     The port to create the server on.
	 * @return The new server.
	 * @throws IOException When an I/O error occurs.
	 */
	public abstract Server createServer(Sessions sessions, int port) throws IOException;
}
