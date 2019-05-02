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
import com.americanlistening.core.net.ServerFactory;
import com.americanlistening.core.sql.SQLInstance;

public class Main {

	public static final boolean functest = true;
	
	public static final int HOST_PORT = 1000;
	
	public static void main(String[] args) {
		final InstanceConfiguration config = new InstanceConfiguration();
		config.args = args;
		config.debug = true;
		config.dontWriteCrashFile = false;
		config.instanceName = "Instance";
		config.logLevel = Level.FINEST;
		config.path = null;
		config.sessionsSeed = 0L;
		config.sqlInstance = SQLInstance.createInstance();
		config.serverFactory = ServerFactory.httpsSecureServerFactory;
		config.userGenerator = new Random();
		config.logFile = "latest.log";
		
		final Instance inst = Instance.createInstance(config);
		
		inst.logger.log(Level.INFO, "Created instance.");
		
		if (functest) {	
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
		} else {
			setup(inst);
		}
	}
	
	public static void setup(Instance inst) {
		CommandHandler chandler = new CommandHandler();
		chandler.addCommand(TestCommand.class);
		
		ClientInputCallback c = new ClientInputCallback(new CommandParser(), chandler);
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
			con.addInputCallback(c);
		});
		inst.logger.log(Level.INFO, "Dispatching server thread...");
		server.dispatchServer();
	}
}
