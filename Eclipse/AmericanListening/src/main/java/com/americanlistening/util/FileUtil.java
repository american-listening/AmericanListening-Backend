package com.americanlistening.util;

import java.io.File;

/**
 * Class containing file utilities.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class FileUtil {

	/**
	 * Deletes a file or directory.
	 * 
	 * @param file
	 *            The file or directory to delete.
	 * @return <code>true</code> if deletion was successful, or <code>false</code>
	 *         if it wasn't.
	 */
	public static boolean delete(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				delete(f);
			}
		}
		return file.delete();
	}
	
	private FileUtil() { }
}
