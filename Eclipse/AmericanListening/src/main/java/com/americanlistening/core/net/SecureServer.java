package com.americanlistening.core.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ServerSocketFactory;

class SecureServer implements Server {
	
	static final String encrPropName = "encryptionType";

	private Sessions sessions;
	private int port;
	private String encryptionType;
	
	private ServerSocket server;
	
	private SecureServerRun srun;
	private boolean shouldRun;
	
	private Map<String, Object> props;
	
	private List<ErrorCallback> eCalls;
	private List<ConnectionCallback> cCalls;
	
	SecureServer(Sessions sessions, int port, String encryptionType) {
		this.props = new HashMap<>();
		this.props.put(encrPropName, encryptionType);
		
		this.sessions = sessions;
		this.port = port;
		
		this.shouldRun = false;
		this.srun = new SecureServerRun();
		
		this.eCalls = new ArrayList<>();
		this.cCalls = new ArrayList<>();
	}
	
	private class SecureServerRun implements Runnable {

		@Override
		public void run() {
			while (shouldRun) {
				try {
					Socket csoc = server.accept();
					for (ConnectionCallback c : cCalls) {
						//c.onConnect(connection);
					}
				} catch (Throwable t) {
					for (ErrorCallback e : eCalls) {
						e.onError(t, Thread.currentThread(), this);
					}
				}
			}
		}
		
	}

	@Override
	public void init() throws IOException {
		server = ServerSocketFactory.getDefault().createServerSocket(port);
	}

	@Override
	public void dispatchServer() {
		if (server == null)
			throw new IllegalStateException("Server is not initialized!");
		Thread t = new Thread(srun);
		t.setName("Server-Thread");
		t.start();
	}

	@Override
	public void stop() throws IOException {
		shouldRun = false;
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
	public boolean isEncrypted() {
		return true;
	}

	@Override
	public Object getProperty(String key) {
		return props.get(key);
	}

	@Override
	public void setProperty(String key, Object value) {
		props.put(key, value);
	}

	@Override
	public boolean hasProperty(String key) {
		return props.containsKey(key);
	}
}
