package com.americanlistening.main;

import java.io.PrintWriter;

import com.americanlistening.core.net.Connection;

public class TestCommand extends Command {

	@Override
	public String friendlyName() {
		return "test";
	}

	@Override
	public void execute(Connection con, String[] args) {
		new PrintWriter(con.outputStream()).println("hello!");
	}

}
