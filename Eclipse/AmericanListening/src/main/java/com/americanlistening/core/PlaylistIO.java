package com.americanlistening.core;

import java.io.File;
import java.io.IOException;

/**
 * Handles playlist input/output in files.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class PlaylistIO {

	/**
	 * Whether to compress playlist data.
	 */
	public static final boolean COMPRESS = true;

	/**
	 * Writes user <code>playlist</code> to file <code>out</code>.
	 * 
	 * @param playlist
	 *            The playlist to write.
	 * @param out
	 *            The output file.
	 * @throws IOException
	 *             When an I/O error occurs.
	 */
	public static void writePlaylist(Playlist playlist, File out) throws IOException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Reads a playlist from file <code>file</code>.
	 * 
	 * @param file
	 *            The file to read from.
	 * @return The new playlist.
	 * @throws IOException
	 *             When an I/O error occurs.
	 */
	public static Playlist readPlaylist(File file) throws IOException {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	private PlaylistIO() { }
}
