package watchlistarr.plex.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenWatchlistFriend {
    public WatchlistData data;
    public TokenWatchlistFriend() {}
}
