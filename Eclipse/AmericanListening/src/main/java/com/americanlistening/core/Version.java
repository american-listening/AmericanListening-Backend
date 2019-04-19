package com.americanlistening.core;

/**
 * Class containing version information.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class Version {

	/**
	 * Version information
	 */
	public static final int VERSION_MAJOR = 1,
							VERSION_MINOR = 0,
							VERSION_REVISION = 0;

	/**
	 * Returns a string representation of the version.
	 * 
	 * @return The version as a string.
	 */
	public static String getVersion() {
		return new StringBuilder().append(VERSION_MAJOR).append('.').append(VERSION_MINOR).append('.')
				.append(VERSION_REVISION).toString();
	}

	private Version() { }
}
