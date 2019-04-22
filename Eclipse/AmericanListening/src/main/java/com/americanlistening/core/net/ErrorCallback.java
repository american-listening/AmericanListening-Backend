package com.americanlistening.core.net;

/**
 * Interface for used when an error has occurred somewhere.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public interface ErrorCallback {

	/**
	 * Called when an exception has occurred.
	 * 
	 * @param e
	 *            The exception thrown.
	 * @param t
	 *            The thread that the exception has thrown on.
	 * @param source
	 *            The source of the error.
	 */
	public void onError(Throwable e, Thread t, Object source);
}
