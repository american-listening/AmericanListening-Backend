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

<<<<<<< HEAD
    private static final int AMT_OF_TOP_RES = 5;    //limited to 10 in TrackSearch class
    private static final String searchTerm = "blood on the leaves";
    private static final int I = 0; 	//Selected by the user on web site

    public static void main(String[] args){

        final JButton findSong = new JButton("Find Song");
        final JLabel label = new JLabel("Nothing", JLabel.CENTER);
        final JLabel time = new JLabel("Time");
        final JTextField timeMin = new JTextField();
        final JLabel colon = new JLabel(":");
        final JTextField timeSec = new JTextField();
        final JButton ok = new JButton("Submit");
        ok.setEnabled(false);
        final JFrame frame = new JFrame ("App");

        frame.setLayout(new GridLayout(3, 4, 5, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        frame.add(label);
        frame.add(findSong);
        frame.add(time);
        frame.add(timeMin);
        frame.add(colon);
        frame.add(timeSec);
        frame.add(ok);






        final Track[] topRes = TrackSearch.searchTracks(searchTerm, AMT_OF_TOP_RES);
        for(int i = 0; i < topRes.length; i++){

            System.out.println(topRes[i].getName());
        }

        findSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                label.setText(topRes[I].getName());
                ok.setEnabled(true);
            }
        });

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            	if((timeMin.getText().equals("")) || ((timeSec.getText().equals("")))) {
            		
            		System.out.println("Fill all fields");
            	}
            	else {
            		
            		int timeInMilli = (Integer.parseInt(timeMin.getText()) * 60000) + (Integer.parseInt(timeSec.getText()) * 1000);
            		
            		Stamp stampObj = new Stamp(0, timeInMilli, topRes[I].getName(), topRes[I].getArtists()[0].getName(), topRes[I].getAlbum().getName(), topRes[I].getAlbum().getImages()[0].getUrl());
            		System.out.println(stampObj.toString());
            		
            	}
            }
        });
    }
=======
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
>>>>>>> ffa6df2c354e197e94c3a5084b485e7b826ebd2d
}
