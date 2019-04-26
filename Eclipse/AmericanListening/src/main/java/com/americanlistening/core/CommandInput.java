package com.americanlistening.core;

import java.util.logging.Level;

import com.americanlistening.core.net.Connection;
import com.americanlistening.core.net.InputCallback;

/**
 * Handles incoming commands from clients.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class CommandInput implements InputCallback {

	private Instance inst;
	
	CommandInput(Instance inst) {
		this.inst = inst;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p><b><code>source</code> must be a <code>Connection</code>.</b></p>
	 */
	@Override
	public void onInput(String msg, Object source) {
		Connection con = (Connection) source;
		inst.logger.log(Level.INFO, "recieved: \"" + msg + "\" from " + con.getAddress());
		// TODO Add input handling from clients
	}
}
