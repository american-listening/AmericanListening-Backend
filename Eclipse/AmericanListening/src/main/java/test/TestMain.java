package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.logging.Level;

import com.americanlistening.core.Instance;
import com.americanlistening.core.User;
import com.americanlistening.core.net.Connection;
import com.americanlistening.core.net.ConnectionFactory;
import com.americanlistening.core.net.Server;

public class TestMain {

	public static void main(String[] args) {
		Instance inst = Instance.createInstance("test_instance", null, args);
		inst.logger.setLevel(Level.ALL);
		try {
			inst.load(null);
			inst.logger.log(Level.INFO, "loaded!");
		} catch (Exception e) {
			inst.logger.log(Level.SEVERE, "failed to load!", e);
		}

		User user = inst.createUser("test@test.com");
		inst.logger.log(Level.INFO, user.toString());

		inst.saveAll();

		Server server = null;

		// Create the server on the current instance
		try {
			inst.createServer(1000);
			inst.logger.log(Level.INFO, "Created server.");
		} catch (IOException e) {
			inst.logger.log(Level.SEVERE, "Failed to create server.", e);
		}

		// Add an error callback and start the server thread
		server = inst.getCurrentServer();
		server.addErrorCallback((Throwable e, Thread t, Object source) -> {
			inst.logger.log(Level.WARNING, "Server error.", e);
		});
		server.dispatchServer();

		try {
			Connection con = ConnectionFactory.secureFactory.createConnection(InetAddress.getByName("localhost"), 1000);
			inst.logger.log(Level.INFO, "Created client.");
			con.addInputCallback((String msg, Object source) -> {
				inst.logger.log(Level.INFO, msg + " from " + source);
			});
			con.addErrorCallback((Throwable e, Thread t, Object source) -> {
				inst.logger.log(Level.WARNING, "Client error.", e);
			});
			PrintWriter out = new PrintWriter(con.outputStream());
			out.println("test");
			con.close();
		} catch (IOException e1) {
			inst.logger.log(Level.SEVERE, "Failed to create connection.", e1);
		}
		
		// Shutdown the server
		if (server != null) {
			try {
				server.stop();
			} catch (IOException e) {
				inst.logger.log(Level.SEVERE, "Failed to create server.");
			}
		}
	}
}
