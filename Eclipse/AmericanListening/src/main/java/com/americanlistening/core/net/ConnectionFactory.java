package com.americanlistening.core.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;

/**
 * A connection factory handles the creation of connections.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public abstract class ConnectionFactory {
	
	public static final ConnectionFactory insecureFactory = new InsecureConnectionFactory();
	public static final ConnectionFactory secureFactory = new SSLConnectionFactory();
	
	private static class SSLConnectionFactory extends ConnectionFactory {

		@Override
		public Connection createConnection(InetAddress address, int port) throws IOException {
			SSLRemoteClient client = new SSLRemoteClient(address, port);
			return client;
		}
		
	}

	private static class InsecureConnectionFactory extends ConnectionFactory {

		@Override
		public Connection createConnection(InetAddress address, int port) throws IOException {
			throw new UnsupportedOperationException("Not supported yet.");
		}
		
	}

	/**
	 * Creates a new connection.
	 * 
	 * @param ip The IP address.
	 * @param port The port.
	 * @return The new connection.
	 * @throws IOException When an I/O error occurs.
	 */
	public Connection createConnection(String ip, int port) throws IOException {
		InetAddress addr = InetAddress.getByName(ip);
		return createConnection(addr, port); 
	}
	
	/**
	 * Creates a new connection.
	 * 
	 * @param address The full IP address.
	 * @return The new connection.
	 * @throws IOException When an I/O error occurs.
	 */
	public Connection createConnection(String address) throws IOException {
		String[] parsed = address.split(":");
		if (parsed.length != 2)
			throw new IllegalArgumentException("Invalid address format.");
		return createConnection(parsed[0], Integer.parseInt(parsed[1]));
	}
	
	/**
	 * Creates a new connection.
	 * 
	 * @param url The url.
	 * @param port The port.
	 * @return The new connection.
	 * @throws IOException When an I/O error occurs.
	 */
	public Connection createConnection(URL url, int port) throws IOException {
		return createConnection(url.getHost(), port);
	}
	
	/**
	 * Creates a new connection.
	 * 
	 * @param url The url.
	 * @param port The port.
	 * @return The new connection.
	 * @throws IOException When an I/O error occurs.
	 */
	public abstract Connection createConnection(InetAddress address, int port) throws IOException;
}
