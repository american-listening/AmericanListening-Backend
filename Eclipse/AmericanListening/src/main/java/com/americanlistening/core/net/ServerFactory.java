package com.americanlistening.core.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory that handles the creation of servers.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public abstract class ServerFactory {

	/**
	 * Factory for the creation of SSL servers.
	 * 
	 * @deprecated Use <code>secureServerFactory</code> instead.
	 */
	@Deprecated
	public static final ServerFactory sslServerFactory = new SSLServerFactory();

	/**
	 * Factory for the creation of secure servers.
	 */
	public static final ServerFactory secureServerFactory = new SecureServerFactory();

	/**
	 * Factory for the creation of HTTPS secure servers.
	 */
	public static final ServerFactory httpsSecureServerFactory = new HttpsSecureServerFactory();

	private static class SSLServerFactory extends ServerFactory {

		@Override
		public Server createServer(Sessions sessions, int port) throws IOException {
			return new SSLServer(sessions, port);
		}
	}

	private static class SecureServerFactory extends ServerFactory {

		private static final String encrPropName = SecureServer.encrPropName;

		@Override
		public Server createServer(Sessions sessions, int port) throws IOException {
			if (!hasProperty(encrPropName))
				throw new IllegalArgumentException("Factory is missing property \"" + encrPropName + "\".");
			return new SecureServer(sessions, port, (String) getProperty(encrPropName));
		}

	}

	private static class HttpsSecureServerFactory extends ServerFactory {

		@Override
		public Server createServer(Sessions sessions, int port) throws IOException {
			return new HttpsSecureServer();
		}

	}

	private Map<String, Object> props;

	/**
	 * Creates a new server factory.
	 */
	public ServerFactory() {
		props = new HashMap<>();
	}

	/**
	 * Sets a property for the factory to use.
	 * 
	 * @param key
	 *            The key to use.
	 * @param value
	 *            The value to use.
	 */
	public void setProperty(String key, Object value) {
		props.put(key, value);
	}

	/**
	 * Returns the property associated with key <code>key</code>.
	 * 
	 * @param key
	 *            The key to use.
	 * @return The associated value.
	 */
	public Object getProperty(String key) {
		return props.get(key);
	}

	/**
	 * Returns whether this factory has a property.
	 * 
	 * @param key
	 *            The key to test for.
	 * @return <code>true</code> if it has it and <code>false</code> otherwise.
	 */
	public boolean hasProperty(String key) {
		return props.containsKey(key);
	}

	/**
	 * Resets the factory's properties.
	 */
	public void reset() {
		props.clear();
	}

	/**
	 * Creates a server on port <code>port</code>.
	 * 
	 * @param sessions
	 *            The client sessions handle to use on the server.
	 * @param port
	 *            The port to create the server on.
	 * @return The new server.
	 * @throws IOException
	 *             When an I/O error occurs.
	 */
	public abstract Server createServer(Sessions sessions, int port) throws IOException;
}
