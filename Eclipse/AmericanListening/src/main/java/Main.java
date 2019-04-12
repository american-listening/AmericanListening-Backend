import com.wrapper.spotify.model_objects.specification.Track;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

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
}
