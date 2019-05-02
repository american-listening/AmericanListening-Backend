package com.americanlistening.main;

import java.io.PrintWriter;

import com.americanlistening.core.net.Connection;
import com.americanlistening.core.net.InputCallback;

public class ClientInputCallback implements InputCallback {
	
	private CommandParser parser;
	private CommandHandler handler;
	
	public ClientInputCallback(CommandParser parser, CommandHandler handler) {
		this.parser = parser;
	}

	@Override
	public void onInput(String msg, Object source) {
		Connection con = (Connection) source;
		processMessage(con, msg);
	}

	private void processMessage(Connection con, String msg) {
		if (msg.startsWith("cmd::")) {
			String smsg = msg.substring(5);
			String[] raw = parser.parseCommand(smsg);
			if (raw.length > 0) {
				Command cmd = handler.get(raw[0]);
				if (cmd != null) {
					// TODO: Get the arguments out of the raw, parsed commands
					cmd.execute(con, null);
				} else {
					new PrintWriter(con.outputStream()).println("cmd::invalid_name");
				}
			}
		}
	}
}
