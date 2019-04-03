import com.wrapper.spotify.model_objects.specification.Track;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private static final int AMT_OF_TOP_RES = 5;    //limited to 10 in TrackSearch class
    private static final String searchTerm = "my love";

    public static void main(String[] args){

        JButton findSong = new JButton("Find Song");
        JLabel label = new JLabel("Nothing", JLabel.CENTER);
        JLabel time = new JLabel("Time");
        JTextField timeMin = new JTextField();
        JLabel colon = new JLabel(":");
        JTextField timeSec = new JTextField();
        JButton ok = new JButton("Submit");
        ok.setEnabled(false);
        JFrame frame = new JFrame ("App");

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

                label.setText(topRes[0].getName());
                ok.setEnabled(true);
            }
        });

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
    }
}
