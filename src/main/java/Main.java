import com.wrapper.spotify.model_objects.specification.Track;

public class Main {

    private static final int AMT_OF_TOP_RES = 5;    //limited to 10 in TrackSearch class
    private static final String searchTerm = "my love";

    public static void main(String[] args){

        final Track[] topRes = TrackSearch.searchTracks(searchTerm, AMT_OF_TOP_RES);
        for(int i = 0; i < topRes.length; i++){

            System.out.println(topRes[i].getName());
        }
    }
}
