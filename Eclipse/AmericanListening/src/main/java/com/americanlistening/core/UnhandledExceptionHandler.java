package com.americanlistening.core;

import static java.util.logging.Level.*;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import com.americanlistening.util.FileUtil;

class UnhandledExceptionHandler implements UncaughtExceptionHandler {

	private Instance currInstance;

	UnhandledExceptionHandler(Instance currInstance) {
		this.currInstance = currInstance;
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		currInstance.logger.setLevel(Level.INFO);
		currInstance.logger.log(SEVERE, "Unhandled exception in Thread \"" + t.getName() + "\"", e);
		currInstance.logger.log(SEVERE, "Attempting to force save everything...");
		boolean suc;
		try {
			suc = currInstance.saveAll();
		} catch (Throwable th) {
			suc = false;
		}
		if (suc)
			currInstance.logger.log(INFO, "Succeeded in saving everything.");
		else
			currInstance.logger.log(SEVERE, "Failed to save everything.");
		currInstance.logger.log(INFO, "Writing to crash file.");
		CrashReport report = new CrashReport(e, t, currInstance);
		try {
			DateFormat dateFormat = new SimpleDateFormat("HH-mm-ss");
			Date date = new Date();
			File f = new File("crash_" + dateFormat.format(date) + ".crash");
			if (f.exists())
				FileUtil.delete(f);
			f.createNewFile();
			report.writeReport(new FileOutputStream(f));
			currInstance.logger.log(INFO, "Wrote to crash file: " + f.getAbsolutePath());
		} catch (Throwable io) {
			currInstance.logger.log(SEVERE, "Failed to write crash file.", io);
		}
		System.exit(-1);
	}

}
