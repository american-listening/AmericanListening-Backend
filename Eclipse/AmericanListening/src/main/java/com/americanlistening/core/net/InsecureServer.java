package com.americanlistening.core.net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * An Insecure Server is an implementation of a HTTP Server. The connections
 * through this server are not secure.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
class InsecureServer implements Server {

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
			String response = "<html><h1>Hello this is a test!</h1><p>This is HTML!</p><p><a href=\"https://www.twitter.com\">This is a link</a></p></html>";
			long threadId = Thread.currentThread().getId();
			System.out.println(t.getProtocol() + " connection from: " + t.getRemoteAddress());
			DateFormat format = new SimpleDateFormat("MM/dd/YYYY HH:mm:ss");
			Date date = new Date();
			response = response + "DEBUG: Thread ID: " + threadId + ", Protocol: " + t.getProtocol()
					+ ", Request Method: " + t.getRequestMethod() + ", Your IP: " + t.getRemoteAddress()
					+ ", Server IP: " + t.getLocalAddress() + ", Response Code: " + t.getResponseCode() + ", Server Time: " + format.format(date);
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
