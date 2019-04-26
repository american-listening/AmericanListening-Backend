package com.americanlistening.core.net;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 * Type of server that supports SSL connections.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
class SSLServer implements Server {

	private SSLServer ref;
	private int port;
	
	private Sessions sessions;
	
	private ServerSocket server;
	private boolean shouldRun;
	private SSLServerRun serverRun;
	
	private List<SSLClientHandle> handles;
	
	private List<ErrorCallback> eCalls;
	private List<ConnectionCallback> cCalls;
	
	SSLServer(Sessions sessions, int port) throws IOException {
		this.sessions = sessions;
		this.port = port;
		
		handles = new ArrayList<>();
		
		eCalls = new ArrayList<>();
		cCalls = new ArrayList<>();
		
		ref = this;
	}
	
	@Override
	public void init() throws IOException {
		SSLServerSocketFactory factory = getServerSocketFactory("TLS");
		server = factory.createServerSocket(port);
	}
	
	/**
	 * Class handling server operations.
	 * 
	 * @author Ethan Vrhel
	 * @since 1.0
	 */
	private class SSLServerRun implements Runnable {

		@Override
		public void run() {
			while (shouldRun) {
				try {
					SSLSocket accept = (SSLSocket) server.accept();
					SSLClientHandle handle = new SSLClientHandle(ref, sessions, accept);
					handles.add(handle);
					for (ConnectionCallback c : cCalls) {
						c.onConnect(handle);
					}
				} catch (Throwable t) {
					for (ErrorCallback c : eCalls) {
						c.onError(t, Thread.currentThread(), this);
					}
				}
			}
		}
		
	}
	
	private SSLServerSocketFactory getServerSocketFactory(String type) {
		if (type.equals("TLS")) {
			SSLServerSocketFactory ssf = null;
			try {
				SSLContext ctx;
				KeyManagerFactory kmf;
				KeyStore ks;
				char[] passphrase = "password".toCharArray();
				
				ctx = SSLContext.getInstance("TLS");
				kmf = KeyManagerFactory.getInstance("SunX509");
				ks = KeyStore.getInstance("JKS");
				
				ks.load(new FileInputStream("keystore.txt"), passphrase);
				kmf.init(ks, passphrase);
				ctx.init(kmf.getKeyManagers(), null, null);
				
				ssf = ctx.getServerSocketFactory();
				return ssf;
			} catch (Exception e) {
				for (ErrorCallback c : eCalls) {
					c.onError(e, Thread.currentThread(), this);
				}
				return null;
			}
		} else {
			return (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		}
	}

	@Override
	public void dispatchServer() {
		serverRun = new SSLServerRun();
		Thread t = new Thread(serverRun);
		t.setName("SSLServer-Thread");
		shouldRun = true;
		t.start();
	}

	@Override
	public void stop() throws IOException {
		for (Connection con : handles) {
			try {
				con.stop();
			} catch (IOException e) {
				System.err.println("Failed to stop: " + con);
			}
		}
		handles.clear();
		shouldRun = false;
		server.close();
	}

	@Override
	public void addErrorCallback(ErrorCallback callback) {
		eCalls.add(callback);
	}
	
	@Override
	public void addConnectionCallback(ConnectionCallback callback) {
		cCalls.add(callback);
	}
	
	@Override
	public String toString() {
		return "SSLServer[port=" + server.getLocalPort() + "]";
	}

}
