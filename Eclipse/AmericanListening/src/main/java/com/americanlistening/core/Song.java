package com.americanlistening.core;

/**
 * Class wrapping song information.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class Song {

	public final long songID, artistID, albumID;
	public final String name, artist, album;
	
	/**
	 * Creates a new song.
	 * 
	 * @param songID Unique song ID.
	 * @param artistID Unique artist ID.
	 * @param albumID Unique album ID.
	 * @param name Song name.
	 * @param artist Artist name.
	 * @param album Album name.
	 */
	public Song(long songID, long artistID, long albumID, String name, String artist, String album) {
		this.songID = songID;
		this.artistID = artistID;
		this.albumID = albumID;
		this.name = name;
		this.artist = artist;
		this.album = album;
	}
}
