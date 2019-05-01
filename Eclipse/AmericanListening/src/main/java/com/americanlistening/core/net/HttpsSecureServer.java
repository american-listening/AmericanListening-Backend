package com.americanlistening.core.net;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

/**
 * Server which communicates using HTTPS.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
class HttpsSecureServer implements Server {

	/**
	 * The port which HTTPS runs on.
	 */
	public static final int HTTPS_PORT = 8080;
	
	private static void printSocketInfo(SSLSocket s, PrintStream out) {
		out.println("Socket class: " + s.getClass());
		out.println("   Remote address = " + s.getInetAddress().toString());
		out.println("   Remote port = " + s.getPort());
		out.println("   Local socket address = " + s.getLocalSocketAddress().toString());
		out.println("   Local address = " + s.getLocalAddress().toString());
		out.println("   Local port = " + s.getLocalPort());
		out.println("   Need client authentication = " + s.getNeedClientAuth());
		SSLSession ss = s.getSession();
		out.println("   Session valid = " + ss.isValid());
		out.println("   Cipher suite = " + ss.getCipherSuite());
		out.println("   Protocol = " + ss.getProtocol());
	}

	private static void printServerSocketInfo(SSLServerSocket s, PrintStream out) {
		out.println("Server socket class: " + s.getClass());
		out.println("   Socket address = " + s.getInetAddress().toString());
		out.println("   Socket port = " + s.getLocalPort());
		out.println("   Need client authentication = " + s.getNeedClientAuth());
		out.println("   Want client authentication = " + s.getWantClientAuth());
		out.println("   Use client mode = " + s.getUseClientMode());
	} 

	private SSLServerSocket server;

	private HttpsSecureServerRun srun;
	private boolean shouldRun;

	private List<ErrorCallback> eCalls;
	private List<ConnectionCallback> cCalls;
	
	private Map<String, Object> props;

	HttpsSecureServer() {
		srun = new HttpsSecureServerRun();
		shouldRun = false;
		eCalls = new ArrayList<>();
		cCalls = new ArrayList<>();
		props = new HashMap<>();
	}

	private class HttpsSecureServerRun implements Runnable {

		@Override
		public void run() {
			while (shouldRun) {
				try {
					SSLSocket soc = (SSLSocket) server.accept();
					System.out.println("before handshake");
					printSocketInfo(soc, System.err);
					soc.startHandshake();
					System.out.println("after handshake");
					printSocketInfo(soc, System.err);
					System.out.println("Client accepted: " + soc.getInetAddress());
					OutputStream out = soc.getOutputStream();
					BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));

					System.out.println("Protocols: " + Arrays.toString(soc.getSSLParameters().getProtocols()));
					System.out.println(soc.toString());
					String line = null;
					while (((line = in.readLine()) != null) && (!("".equals(line)))) {
						System.out.println(line);
					}
					StringBuffer buffer = new StringBuffer();
					buffer.append("<HTML>\n");
					buffer.append("<HEAD><TITLE>HTTPS Server</TITLE></HEAD>\n");
					buffer.append("<BODY>\n");
					buffer.append("<H1>Success!</H1>\n");
					buffer.append("</BODY>\n");
					buffer.append("</HTML>\n");

					String string = buffer.toString();
					byte[] data = string.getBytes();
					out.write("HTTP/1.0 200 OK\n".getBytes());
					out.write(new String("Content-Length: " + data.length + "\n").getBytes());
					out.write("Content-Type: text/html\n\n".getBytes());
					out.write(data);
					out.flush();

					out.close();
					in.close();
					server.close();
				} catch (Throwable t) {
					for (ErrorCallback e : eCalls) {
						e.onError(t, Thread.currentThread(), this);
					}
				} finally {
					shouldRun = false;
				}
			}
		}
	}

	@Override
	public void init() throws IOException {
		try {
//			SSLContext ctx;
//			KeyManagerFactory kmf;
//			KeyStore ks;
//			char[] passphrase = "password".toCharArray();
//
//			ctx = SSLContext.getInstance("TLSv1.2");
//			kmf = KeyManagerFactory.getInstance("SunX509");
//			ks = KeyStore.getInstance("JKS");
//
//			ks.load(new FileInputStream("store.keystore"), passphrase);
//			kmf.init(ks, passphrase);
//			ctx.init(kmf.getKeyManagers(), null, null);
//
//			System.err.println("Server is using provider: " + ctx.getProvider().getInfo());
//
//			SSLServerSocketFactory f = ctx.getServerSocketFactory();

//			SSLContext context = SSLContext.getInstance("TLSv1.2");
//			context.init(null, null, null);
//			SSLServerSocketFactory f = context.getServerSocketFactory();
//
		/*	KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(new FileInputStream("store.keystore"), "password".toCharArray());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, "password".toCharArray());
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(kmf.getKeyManagers(), null, null);
			*/
			SSLServerSocketFactory f = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			server = (SSLServerSocket) f.createServerSocket(HTTPS_PORT);
			System.out.println("Protocols: " + Arrays.toString(server.getEnabledProtocols()));

			printServerSocketInfo(server, System.err);
		} catch (Exception e) {
			throw new IOException("Failed to initialize. (" + e + ")", e);
		}
	}

	@Override
	public void dispatchServer() {
		if (server == null)
			throw new IllegalStateException("Server is not initialized.");
		shouldRun = true;
		Thread t = new Thread(srun);
		t.setName("HTTPS-Server-Thread");
		t.start();
	}

	@Override
	public void stop() throws IOException {
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
