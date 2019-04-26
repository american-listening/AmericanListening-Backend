package com.americanlistening.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;

import com.americanlistening.core.Instance;
import com.americanlistening.core.InstanceConfiguration;
import com.americanlistening.core.net.Connection;
import com.americanlistening.core.net.ConnectionFactory;
import com.americanlistening.core.net.Server;

public class Main {

	public static final int HOST_PORT = 1000;
	
	public static void main(String[] args) {
		final InstanceConfiguration config = new InstanceConfiguration();
		config.args = args;
		config.debug = true;
		config.dontWriteCrashFile = true;
		config.instanceName = "Instance";
		config.logLevel = Level.FINEST;
		config.path = null;
		config.sessionsSeed = 0L;
		config.userGenerator = new Random();
		config.logFile = "latest.log";
		
		final Instance inst = Instance.createInstance(config);
		
		inst.logger.log(Level.INFO, "Created instance.");
		
		try {
			inst.createServer(HOST_PORT);
			inst.logger.log(Level.INFO, "Created server on port " + HOST_PORT);
		} catch (IOException e) {
			inst.logger.log(Level.SEVERE, "Failed to create server.", e);
		}
		
		Server server = inst.getCurrentServer();
		server.addErrorCallback((Throwable e, Thread t, Object source) -> {
			inst.logger.log(Level.SEVERE, "Server error.", e);
		});
		try {
			server.init();
		} catch (IOException e) {
			inst.logger.log(Level.SEVERE, "Failed to initialize server.", e);
		}
		server.addConnectionCallback((Connection con) -> {
			inst.logger.log(Level.FINE, "Connect: " + con.getAddress() + ":" + con.getPort());
			con.addInputCallback(inst.commandCallback);
		});
		inst.logger.log(Level.INFO, "Dispatching server thread...");
		server.dispatchServer();
		
		Connection con = null;
		try {
			con = ConnectionFactory.secureFactory.createConnection("localhost", HOST_PORT);
			inst.logger.log(Level.INFO, "Created connection to: " + con.getAddress());
		} catch (IOException e1) {
			inst.logger.log(Level.SEVERE, "Failed to connect to.", e1);
		}
		
		PrintWriter out = new PrintWriter(con.outputStream());
		out.println("Hello World!");
		
//		try {
//			server.stop();
//			inst.logger.log(Level.INFO, "Stopped server.");
//		} catch (IOException e) {
//			inst.logger.log(Level.SEVERE, "Failed to stop server.");
//			System.exit(-1);
//		}
	}
}
