package watchlistarr.plex.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WatchlistUserData {
    public WatchlistNodes watchlist;
    public WatchlistUserData() {}
}
