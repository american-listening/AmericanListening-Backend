package com.americanlistening.core;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;

import com.americanlistening.core.net.ServerFactory;
import com.americanlistening.core.sql.SQLInstance;

/**
 * An instance configuration contains configuration information about how to
 * make an instance object.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class InstanceConfiguration implements Cloneable {
	
	public boolean debug;
	public boolean dontWriteCrashFile;
	
	public String instanceName, path;
	public Random userGenerator;
	public long sessionsSeed;
	public SQLInstance sqlInstance;
	
	public ServerFactory serverFactory;
	
	public Level logLevel;
	public String logFile;
	
	public String[] args;

	@Override
	public InstanceConfiguration clone() {
		InstanceConfiguration config = new InstanceConfiguration();
		config.instanceName = instanceName;
		config.path = path;
		config.debug = debug;
		config.dontWriteCrashFile = dontWriteCrashFile;
		config.userGenerator = userGenerator;
		config.sessionsSeed = sessionsSeed;
		config.sqlInstance = sqlInstance;
		config.serverFactory = serverFactory;
		config.logLevel = logLevel;
		config.logFile = logFile;
		config.args = Arrays.copyOf(args, args.length);
		return config;
	}
}
