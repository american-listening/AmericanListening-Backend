import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.io.IOException;


public class TrackSearch{


    private static final String clientId = "2c3d2a8e718e432880b2601c0fc248a9";
    private static final String clientSecret = "39ec4e439d734605bf1a6be4ddffcc15";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .build();
    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();



    public static Track[] searchTracks(String searchTerm, int amtOfRes) {
        try {

            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            final SpotifyApi api = new SpotifyApi.Builder()
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setAccessToken(clientCredentials.getAccessToken())
                    .build();

            System.out.println("Expires in: " + clientCredentials.getExpiresIn());

            //calls private helper method
            return searchTracks(searchTerm, api, amtOfRes);

        }

        catch (IOException | SpotifyWebApiException e) {

            System.out.println("Error: " + e.getMessage());
            return null;

        }
    }

    //private helper method
    private static Track[] searchTracks(String searchTerm, SpotifyApi api, int amtOfRes) {
        try {
            final Track[] topRes = new Track[amtOfRes];
            final SearchTracksRequest searchTracksRequest = api.searchTracks(searchTerm)
                    .market(CountryCode.US)
                    .limit(10)
                    .offset(0)
                    .build();

            final Paging<Track> trackPaging = searchTracksRequest.execute();

            System.out.println("Total: " + trackPaging.getTotal());
            for(int i = 0; i < amtOfRes; i++){

                topRes[i] = trackPaging.getItems()[i];
            }

            return topRes;

        }

        catch (IOException | SpotifyWebApiException e) {

            System.out.println("Error: " + e.getMessage());
            return null;

        }
    }


}