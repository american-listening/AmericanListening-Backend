package com.americanlistening.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An instance is the base functionality class for the platform.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class Instance {

	/**
	 * The subdirectory for all data.
	 */
	public static final String DATA_SUBDIRECTORY = "data\\";

	/**
	 * The subdirectory for user data.
	 */
	public static final String USER_SUBDIRECTORY = "users\\";

	/**
	 * The subdirectory for playlist data.
	 */
	public static final String PLAYLIST_SUBDIRECTORY = "playlists\\";

	/**
	 * Creates a new instance.
	 * 
	 * @param instanceName
	 *            The name of the instance.
	 * @param path
	 *            The working directory of the instance.
	 * @return The new instance.
	 */
	public static Instance createInstance(String instanceName, String path) {
		return new Instance(instanceName, path);
	}

	/**
	 * The logger for this instance.
	 */
	public final Logger logger;

	private String name;
	private String path;

	private Map<Long, User> users;
	private Map<Long, Playlist> playlists;

	private Random userGenerator;

	// TODO: Implement playlists
	// private Map<Long, Playlist> playlists;

	private Instance(String name, String path) {
		this.name = name;
		this.path = path == null ? "" : path;
		this.users = new HashMap<>();
		this.playlists = new HashMap<>();
		this.logger = Logger.getLogger(name);
		this.userGenerator = new Random();
		Thread.setDefaultUncaughtExceptionHandler(new UnhandledExceptionHandler(this));
	}

	/**
	 * Returns the name of this instance.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Loads all data from path <code>path</code>.
	 * 
	 * @param path
	 *            The path to load from.
	 * @throws IOException
	 *             When an I/O error occurs.
	 */
	public void load(String path) throws IOException {
		path = path == null ? "" : path;

		// Load users
		File userDir = new File(path + DATA_SUBDIRECTORY + USER_SUBDIRECTORY);
		if (!userDir.exists())
			userDir.mkdirs();
		File[] userFiles = userDir.listFiles();
		for (File file : userFiles) {
			User us = UserIO.readUser(file);
			users.put(us.id, us);
		}

		// Load playlists
		File playlistDir = new File(path + DATA_SUBDIRECTORY + PLAYLIST_SUBDIRECTORY);
		if (!playlistDir.exists())
			playlistDir.mkdirs();
		File[] playlistFiles = playlistDir.listFiles();
		for (File file : playlistFiles) {
			Playlist pl = PlaylistIO.readPlaylist(file);
			playlists.put(pl.id, pl);
		}
	}

	/**
	 * Creates a new user with email <code>email</code>.
	 * 
	 * @param email
	 *            The email.
	 * @return The new user.
	 */
	public User createUser(String email) {
		User nuser = new User(userGenerator.nextLong());
		nuser.setEmail(email);
		users.put(nuser.id, nuser);
		logger.log(Level.FINER, "Created user with id " + nuser.id);
		return nuser;
	}

	/**
	 * Saves user <code>user</code> to a file.
	 * 
	 * @param user
	 *            The user to save.
	 * @throws IOException
	 *             When an I/O error occurs.
	 */
	public void saveUser(User user) throws IOException {
		File outp = new File(path + DATA_SUBDIRECTORY + USER_SUBDIRECTORY + user.id + ".udata");
		UserIO.writeUser(user, outp);
	}

	/**
	 * Saves playlist <code>playlist</code> to a file.
	 * 
	 * @param playlist
	 *            The playlist to save.
	 * @throws IOException
	 *             When an I/O error occurs.
	 */
	public void savePlaylist(Playlist playlist) throws IOException {
		File outp = new File(path + DATA_SUBDIRECTORY + PLAYLIST_SUBDIRECTORY + playlist.id + ".pdata");
		PlaylistIO.writePlaylist(playlist, outp);
	}
	
	/**
	 * Saves all data.
	 */
	public void saveAll() {
		Collection<User> uset = users.values();
		int saved = 0;
		for (User user : uset) {
			try {
				saveUser(user);
				saved++;
			} catch (IOException e) {
				logger.log(Level.WARNING, "Failed to save user " + user + " " + e);
				logger.log(Level.FINE, null, e);
			}
		}
		logger.log(Level.INFO, "Saved " + saved + " users.");
		
		saved = 0;
		Collection<Playlist> pset = playlists.values();
		for (Playlist playlist : pset) {
			try {
				savePlaylist(playlist);
				saved++;
			} catch (IOException e) {
				logger.log(Level.WARNING, "Failed to save playlist " + playlist + " " + e);
				logger.log(Level.FINE, null, e);
			}
		}
		logger.log(Level.INFO, "Saved " + saved + " playlists.");
	}

	/**
	 * Returns a user of id <code>id</code>.
	 * 
	 * @param id
	 *            The user id.
	 * @return The respective user, or <code>null</code> if it doesn't exist.
	 */
	public User getUser(long id) {
		return users.get(id);
	}

	/**
	 * Returns users with username <code>username</code>, ignoring capitalization.
	 * If capitalization must be compared, use <code>usersWithExplicitName</code>.
	 * 
	 * @param username
	 *            The username to test for.
	 * @return The users with username <code>username</code>.
	 */
	public User[] usersWithName(String username) {
		Collection<User> userCol = users.values();
		List<User> userLs = new ArrayList<>();
		for (User user : userCol) {
			if (user.getUsername().equalsIgnoreCase(username))
				userLs.add(user);
		}
		User[] userarr = new User[userLs.size()];
		userarr = userLs.toArray(userarr);
		return userarr;
	}

	/**
	 * Returns users with username <code>username</code>, comparing capitalization.
	 * If capitalization shouldn't be compared, use <code>usersWithName</code>.
	 * 
	 * @param username
	 *            The username to test for.
	 * @return The users with username <code>username</code>.
	 */
	public User[] usersWithExplicitName(String username) {
		Collection<User> userCol = users.values();
		List<User> userLs = new ArrayList<>();
		for (User user : userCol) {
			if (user.getUsername().equals(username))
				userLs.add(user);
		}
		User[] userarr = new User[userLs.size()];
		userarr = userLs.toArray(userarr);
		return userarr;
	}
}
