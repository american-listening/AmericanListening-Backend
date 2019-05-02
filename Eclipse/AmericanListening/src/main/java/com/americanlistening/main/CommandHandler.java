package com.americanlistening.main;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

	private Map<String, Command> cmds;
	
	public CommandHandler() {
		cmds = new HashMap<>();
	}
	
	public void addCommand(Class<? extends Command> cmdc) throws IllegalStateException {
		try {
			Command cmd = cmdc.newInstance();
			cmds.put(cmd.friendlyName(), cmd);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalStateException("Failed to instantiate command.", e);
		}
	}
	
	public Command get(String commandName) {
		return cmds.get(commandName);
	}
	
	@Override
	public String toString() {
		return "CommandHandler[numComands=" + cmds.size() + "]";
	}
}
