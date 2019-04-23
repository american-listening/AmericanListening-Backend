package com.americanlistening.core.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLServerSocket;
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
	
	private Sessions sessions;
	
	private ServerSocket server;
	private boolean shouldRun;
	private SSLServerRun serverRun;
	
	private List<SSLClientHandle> handles;
	
	private List<ErrorCallback> eCalls;
	
	SSLServer(Sessions sessions, int port) throws IOException {
		this.sessions = sessions;
		
		SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		server = factory.createServerSocket(port);
		
		handles = new ArrayList<>();
		
		eCalls = new ArrayList<>();
		
		ref = this;
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
				} catch (Throwable t) {
					for (ErrorCallback c : eCalls) {
						c.onError(t, Thread.currentThread(), this);
					}
				}
			}
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
	public String toString() {
		return "SSLServer[port=" + server.getLocalPort() + "]";
	}
}
