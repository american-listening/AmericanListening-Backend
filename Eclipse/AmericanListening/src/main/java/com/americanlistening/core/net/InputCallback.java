package com.americanlistening.core.net;

/**
 * Interface for used when an input has been received somewhere.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
@FunctionalInterface
public interface InputCallback {

	/**
	 * Called when an input has been detected.
	 * 
	 * @param msg
	 *            The sent message.
	 * @param source
	 *            The source of the input.
	 */
	public void onInput(String msg, Object source);
}
