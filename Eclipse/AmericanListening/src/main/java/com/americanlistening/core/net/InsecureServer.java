package com.americanlistening.core.net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

class InsecureServer implements Server {

	private static volatile int scid = 0;

	private Map<String, Object> props;

	private Sessions sessions;
	private boolean shouldRun;

	private HttpServer server;
	private int port;

	private List<ErrorCallback> eCalls;
	private List<ConnectionCallback> cCalls;

	private InsecureServerHandler srun;

	InsecureServer(Sessions sessions, int port) {
		this.props = new HashMap<>();
		this.eCalls = new ArrayList<>();
		this.cCalls = new ArrayList<>();

		this.sessions = sessions;
		this.port = port;
		this.shouldRun = false;
		this.srun = new InsecureServerHandler();
	}

	private class InsecureServerHandler implements HttpHandler, Runnable {

		@Override
		public void run() {
	
		}

		@Override
		public void handle(HttpExchange t) throws IOException {
			for (ConnectionCallback c : cCalls) {
				c.onConnect(null);
			}
			String response = "This is the response";
            long threadId = Thread.currentThread().getId();
            System.out.println("I am thread " + threadId );
            response = response + "Thread Id = "+threadId;
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
		}

	}

	@Override
	public void init() throws IOException {
		server = HttpServer.create(new InetSocketAddress(port), 0);
	}

	@Override
	public void dispatchServer() {
		server.createContext("/test", srun);
		server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
		server.start();
	}

	@Override
	public void stop() throws IOException {
		server.stop(0);
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
		return false;
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
