package watchlistarr.plex.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WatchlistData {
    public WatchlistUserData user;
    public WatchlistData() {}
}
