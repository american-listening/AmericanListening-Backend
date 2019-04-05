import com.wrapper.spotify.model_objects.specification.Track;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main class.
 * 
 * @author Ethan Vrhel
 * @author Hrik Bhowal
 * @author Abosh Upadhyaya
 */
public class Main {

	private static final int AMT_OF_TOP_RES = 5; // limited to 10 in TrackSearch class
	private static final int I = 0; // Selected by the user on web site

	/**
	 * Main method.
	 * 
	 * @param args The program arguments.
	 */
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			System.err.println("Failed to set look and feel (" + e1 + ")");
		}

		Gui gui = new Gui();
		SwingUtilities.invokeLater(() -> gui.setVisible(true));
	}

	/**
	 * Searches for a song.
	 * 
	 * @param term The term to use for the search.
	 * @return An array of the top searches.
	 */
	public static Track[] search(String term) {
		final Track[] top = TrackSearch.searchTracks(term, AMT_OF_TOP_RES);
		System.out.println("Results:");
		for (Track track : top) {
			System.out.println("\t" + track.getName() + " - " + track.getArtists()[0].getName());
		}
		System.out.println("End");
		return top;
	}

	/**
	 * Converts a string into a number in milliseconds.
	 * 
	 * @param time The time to parse.
	 * @return The parsed time in milliseconds.
	 * @throws IllegalArgumentException When the time is invalid.
	 */
	public static long parseTime(String time) throws IllegalArgumentException {
		String[] tokens = time.split(":");
		if (tokens.length != 2)
			throw new IllegalArgumentException("Invalid time.");
		try {
			int min = Integer.parseInt(tokens[0]);
			int sec = Integer.parseInt(tokens[1]);
			return (min * 1000 * 60) + (sec * 1000);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Failed to parse.", e);
		}
	}

	public static String toTime(long millis) {
		long seconds = millis / 1000;
		int min = (int) (seconds / 60);
		int sec = (int) (seconds % 60);
		return sec < 10 ? min + ":0" + sec : min + ":" + sec;
	}
}
