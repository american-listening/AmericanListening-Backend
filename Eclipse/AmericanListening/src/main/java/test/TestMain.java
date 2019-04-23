package test;

import java.io.IOException;
import java.util.logging.Level;

import com.americanlistening.core.Instance;
import com.americanlistening.core.User;
import com.americanlistening.core.net.Server;

public class TestMain {

	public static void main(String[] args) {
		Instance inst = Instance.createInstance("test_instance", null);
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
		} catch (IOException e) {
			inst.logger.log(Level.SEVERE, "Failed to create server.");
		}

		// Add an error callback and start the server thread
		server = inst.getCurrentServer();
		server.addErrorCallback((Throwable e, Thread t, Object source) -> {
			inst.logger.log(Level.WARNING, "Server error.", e);
		});
		server.dispatchServer();

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
