package com.americanlistening.core.net;

/**
 * Interface representing an object which can be exported to a string.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
@FunctionalInterface
public interface ExportableObject {

	/**
	 * Exports this object to a string.
	 * 
	 * @return The exported string.
	 */
	public String export();
}
