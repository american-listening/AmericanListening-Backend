package com.americanlistening.core;

import static java.util.logging.Level.*;

import java.lang.Thread.UncaughtExceptionHandler;

class UnhandledExceptionHandler implements UncaughtExceptionHandler {

	private Instance currInstance;

	UnhandledExceptionHandler(Instance currInstance) {
		this.currInstance = currInstance;
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		currInstance.logger.log(SEVERE, "Unhandled exception in Thread \"" + t.getName() + "\"", e);
	}

}
