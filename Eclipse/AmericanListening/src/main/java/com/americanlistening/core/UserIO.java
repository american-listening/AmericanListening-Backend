package com.americanlistening.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Handles user input/output in files.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class UserIO {

	/**
	 * Whether to compress user data.
	 */
	public static final boolean COMPRESS = true;

	/**
	 * Writes user <code>user</code> to file <code>out</code>.
	 * 
	 * @param user
	 *            The user to write.
	 * @param fout
	 *            The output file.
	 * @throws IOException
	 *             When an I/O error occurs.
	 */
	public static void writeUser(User user, File fout) throws IOException {
		if (!fout.exists())
			fout.createNewFile();
		PrintWriter out = new PrintWriter(fout);
		out.println("id=" + user.id);
		out.println("username=" + user.getUsername());
		out.println("following=" + Arrays.toString(user.getFollowingIDs()));
		out.println("followers=" + Arrays.toString(user.getFollowerIDs()));
		out.println("profileImage=" + user.getProfileImage());
		out.println("location=" + user.getLocation());
		out.println("age=" + user.getAge());
		out.println("email=" + user.getEmail());
		out.println("history=" + user.getEventHistory().values().toString());
		out.flush();
		out.close();
	}

	/**
	 * Reads a user from file <code>file</code>.
	 * 
	 * @param file
	 *            The file to read from.
	 * @return The new user.
	 * @throws IOException
	 *             When an I/O error occurs.
	 */
	public static User readUser(File file) throws IOException {
		if (!file.exists())
			throw new FileNotFoundException("Nonexistent file " + file.getAbsolutePath());
		if (file.isDirectory())
			throw new FileNotFoundException(file.getAbsolutePath() + " is not a file.");
		List<String> lines = new ArrayList<>();
		Scanner in = new Scanner(file);
		while (in.hasNextLine()) {
			lines.add(in.nextLine());
		}
		in.close();
		return null;
	}
	
	private UserIO() { }
}
