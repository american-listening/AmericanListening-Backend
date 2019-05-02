package com.americanlistening.main;

import com.americanlistening.core.net.Connection;

public abstract class Command {
	
	public abstract String friendlyName();

	public abstract void execute(Connection con, String[] args);
}
