package watchlistarr.plex.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendsData {
    public List<FriendV2> allFriendsV2 = List.of();
    public FriendsData() {}
}
