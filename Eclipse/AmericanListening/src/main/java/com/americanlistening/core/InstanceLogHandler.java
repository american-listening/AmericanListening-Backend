package com.americanlistening.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

class InstanceLogHandler extends Handler {

	private File f;
	private PrintWriter out;
	
	InstanceLogHandler(String fileOutput, Logger logger) {
		f = new File(fileOutput);
		if (!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				logger.log(Level.WARNING, "Failed to create log file.", e);
			}
		try {
			out = new PrintWriter(f);
		} catch (FileNotFoundException e) {
			logger.log(Level.WARNING, "Failed to create log printwriter.", e);
		}
	}

	@Override
	public void close() throws SecurityException { }

	@Override
	public void flush() { }

	@Override
	public void publish(LogRecord arg0) {
		Level lvl = arg0.getLevel();

		StringBuilder builder = new StringBuilder();
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String date = format.format(new Date());
		
		builder.append("[" + date + "]");
		
		builder.append(" ");
		
		builder.append("[" + lvl.getName() + "]");
		
		builder.append(" ");
		
		builder.append(arg0.getSourceClassName() + "." + arg0.getSourceMethodName() + ": ");
		builder.append(arg0.getMessage());
		
		Throwable t = arg0.getThrown();
		if (t != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			builder.append(sw.toString());
		}
		
		String full = builder.toString();
		if (f != null) {
			out.println(full);
			out.flush();
		}
		System.out.println(full);
	}

}
