package com.americanlistening.core.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * Type of connection that supports SSL communications.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
class SSLClient implements Connection {

	static {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
	}

	private InetAddress addr;
	private int port;

	private SSLSocket socket;
	private OutputStream out;
	private InputStream in;

	private SSLClientRead clientRead;
	private boolean shouldRun;

	private List<InputCallback> iCalls;
	private List<ErrorCallback> eCalls;

	SSLClient(InetAddress addr, int port) throws IOException {
		this.addr = addr;
		this.port = port;

		// Create socket and connect
		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		socket = (SSLSocket) factory.createSocket(addr, port);
		
		socket.startHandshake();

		out = socket.getOutputStream();
		in = socket.getInputStream();

		iCalls = new ArrayList<>();
		eCalls = new ArrayList<>();
	}

	private class SSLClientRead implements Runnable {

		@Override
		public void run() {
			BufferedReader bin = new BufferedReader(new InputStreamReader(in));
			while (shouldRun) {
				try {
					String msg = bin.readLine();
					for (InputCallback c : iCalls) {
						c.onInput(msg, addr.getHostAddress());
					}
				} catch (Throwable t) {
					for (ErrorCallback c : eCalls) {
						c.onError(t, Thread.currentThread(), this);
					}
				}
			}
		}

	}

	@Override
	public InputStream inputStream() {
		return in;
	}

	@Override
	public OutputStream outputStream() {
		return out;
	}

	@Override
	public void start() {
		clientRead = new SSLClientRead();
		Thread t0 = new Thread(clientRead);
		t0.setName("SSL-Client-Read-Thread");

		shouldRun = true;
		t0.start();
	}

	@Override
	public void stop() throws IOException {
		shouldRun = false;
		socket.close();
	}

	@Override
	public void close() throws IOException {
		shouldRun = false;
		socket.close();
	}

	@Override
	public boolean isConnected() {
		return socket.isConnected();
	}

	@Override
	public boolean isSecure() {
		return true;
	}

	@Override
	public InetAddress getAddress() {
		if (!isConnected())
			return null;
		return addr;
	}

	@Override
	public int getPort() {
		if (socket.isClosed() || !socket.isConnected())
			return -1;
		return port;
	}

	@Override
	public void addInputCallback(InputCallback callback) {
		iCalls.add(callback);
	}

	@Override
	public void addErrorCallback(ErrorCallback callback) {
		eCalls.add(callback);
	}

	@Override
	public ClientSession getSession() {
		return null;
	}
}
