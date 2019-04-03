
public class Stamp {
	
	private int stampId;
	private int timeInMilli;
	private String trackName;
	private String trackArtist;
	private String trackAlbum;
	private String albumCover;
	
	public Stamp(int stampId, int timeInMilli, String trackName, String trackArtist, String trackAlbum, String albumCover) {

		this.stampId = stampId;
		this.timeInMilli = timeInMilli;
		this.trackName = trackName;
		this.trackArtist = trackArtist;
		this.trackAlbum = trackAlbum;
		this.albumCover = albumCover;
		
	}

	public int getStampId() {
		return stampId;
	}

	public void setStampId(int stampId) {
		this.stampId = stampId;
	}

	public int getTimeInMilli() {
		return timeInMilli;
	}

	public void setTimeInMilli(int timeInMilli) {
		this.timeInMilli = timeInMilli;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public String getTrackArtist() {
		return trackArtist;
	}

	public void setTrackArtist(String trackArtist) {
		this.trackArtist = trackArtist;
	}

	public String getTrackAlbum() {
		return trackAlbum;
	}

	public void setTrackAlbum(String trackAlbum) {
		this.trackAlbum = trackAlbum;
	}

	public String getAlbumCover() {
		return albumCover;
	}

	public void setAlbumCover(String albumCover) {
		this.albumCover = albumCover;
	}
	
	public String toString() {
		
		return getTrackName() + " by " + getTrackArtist() + " on " + getTrackAlbum() + " at " + getTimeInMilli() + " milliseconds.";
	}
	
		

}
