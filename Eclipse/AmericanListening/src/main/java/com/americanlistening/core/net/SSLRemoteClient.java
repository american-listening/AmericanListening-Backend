package com.americanlistening.core.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.security.Security;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

class SSLRemoteClient implements Connection {

	static {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
	}

	private InetAddress addr;
	private int port;
	
	private SSLSocket socket;
	private OutputStream out;
	private InputStream in;

	SSLRemoteClient(InetAddress addr, int port) throws IOException {
		this.addr = addr;
		this.port = port;
		
		// Create socket and connect
		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		socket = (SSLSocket) factory.createSocket(addr, port);
		
		out = socket.getOutputStream();
		in = socket.getInputStream();
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
	public void close() throws IOException {
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
		if (!isConnected())
			return -1;
		return port;
	}
}
