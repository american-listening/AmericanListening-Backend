package com.americanlistening.core;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;

/**
 * An instance configuration contains configuration information about how to
 * make an instance object.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class InstanceConfiguration implements Cloneable {
	
	public boolean debug;
	
	public String instanceName, path;
	public Random userGenerator;
	public long sessionsSeed;
	
	public Level logLevel;
	
	public String[] args;

	@Override
	public InstanceConfiguration clone() {
		InstanceConfiguration config = new InstanceConfiguration();
		config.instanceName = instanceName;
		config.path = path;
		config.debug = debug;
		config.userGenerator = userGenerator;
		config.sessionsSeed = sessionsSeed;
		config.logLevel = logLevel;
		config.args = Arrays.copyOf(args, args.length);
		return config;
	}
}
