package com.americanlistening.core.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

/**
 * Handle for a client connected to a server secured by SSL.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class SSLClientHandle implements Runnable, Connection {

	private SSLServer server;

	private SSLSocket socket;
	private InputStream in;
	private OutputStream out;

	private List<InputCallback> iCalls;
	private List<ErrorCallback> eCalls;

	private boolean shouldRun;

	private ClientSessionImpl session;

	SSLClientHandle(SSLServer server, Sessions sessions, SSLSocket socket) throws IOException {
		SSLSession session = socket.getSession();
		Certificate[] cchain2 = session.getLocalCertificates();
		if (cchain2 == null) {
			System.err.println("Certificates are null!  n");
		}
		for (Certificate c : cchain2) {
			System.out.println(((X509Certificate) c).getSubjectDN());
		}
		System.err.println("Peer host is " + session.getPeerHost());
		System.err.println("Cipher is " + session.getCipherSuite());
		System.err.println("Protocol is " + session.getProtocol());
		System.err.println("ID is " + new BigInteger(session.getId()));
		System.err.println("Session created in " + session.getCreationTime());
		System.err.println("Session accessed in " + session.getLastAccessedTime());

		this.server = server;
		this.socket = socket;
		this.in = socket.getInputStream();
		this.out = socket.getOutputStream();
		this.session = new ClientSessionImpl(sessions, this);
		sessions.addSession(this.session);
		shouldRun = true;
		iCalls = new ArrayList<>();
		eCalls = new ArrayList<>();
		Thread t = new Thread(this);
		t.setName("SSL-Client-Handle-Thread");
		t.setDaemon(true);
		t.start();
	}

	@Override
	public void run() {
		BufferedReader bin = new BufferedReader(new InputStreamReader(in));
		while (shouldRun) {
			try {
				String read = bin.readLine();
				for (InputCallback i : iCalls) {
					i.onInput(read, this);
				}
			} catch (Throwable t) {
				for (ErrorCallback e : eCalls) {
					e.onError(t, Thread.currentThread(), this);
				}
			}
		}
	}

	boolean isValid() {
		return !socket.isClosed();
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
		throw new UnsupportedOperationException("Cannot explicitly start a SSLClientHandle.");
	}

	@Override
	public void stop() throws IOException {
		session.freeSession();
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
		return socket.getInetAddress();
	}

	@Override
	public int getPort() {
		if (socket.isClosed() || !socket.isConnected())
			return -1;
		return socket.getPort();
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
	public void close() throws IOException {
		session.freeSession();
		shouldRun = false;
		socket.close();
	}

	@Override
	public ClientSession getSession() {
		return session;
	}
}
