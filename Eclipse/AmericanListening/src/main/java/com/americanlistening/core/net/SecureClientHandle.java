package com.americanlistening.core.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class SecureClientHandle implements Connection {
	
	private Server server;
	private Sessions sessions;
	private Socket client;
	
	private ClientHandleThread ch;
	private boolean shouldRun;
	
	private List<ErrorCallback> eCalls;
	private List<InputCallback> iCalls;
	
	SecureClientHandle(Server server, Sessions sessions, Socket client) {
		this.server = server;
		this.sessions = sessions;
		this.client = client;
		this.ch = new ClientHandleThread();
		this.shouldRun = false;
		this.eCalls = new ArrayList<>();
		this.iCalls = new ArrayList<>();
	}

	private class ClientHandleThread implements Runnable {

		@Override
		public void run() {
			while (shouldRun) {
				
			}
		}
		
	}
	
	@Override
	public InputStream inputStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputStream outputStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public InetAddress getAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addInputCallback(InputCallback callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addErrorCallback(ErrorCallback callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ClientSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

}