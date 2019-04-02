import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class TrackSearch{

    public static void main(String[] args){

        searchTracks();

    }


    private static final String clientId = "2c3d2a8e718e432880b2601c0fc248a9";
    private static final String clientSecret = "39ec4e439d734605bf1a6be4ddffcc15";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setAccessToken("BQAG-_iWwdisBCRSolK9HWJLbSMWRpeZ4udl1Kq_KGI6yDHIarKS0JKXFSHGi8ydyztRol_7NFKdV0s6rJc")
            .build();
    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();



    public static void searchTracks() {
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            final SpotifyApi api = new SpotifyApi.Builder()
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setAccessToken(clientCredentials.getAccessToken())
                    .build();

            //calls private helper method
            searchTracks(api);

            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //private helper method
    private static void searchTracks(SpotifyApi api) {
        try {

            final SearchTracksRequest searchTracksRequest = api.searchTracks("another")
                    .market(CountryCode.US)
                    .limit(10)
                    .offset(0)
                    .build();

            final Paging<Track> trackPaging = searchTracksRequest.execute();

            System.out.println("Total: " + trackPaging.getItems()[1].getName());
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}