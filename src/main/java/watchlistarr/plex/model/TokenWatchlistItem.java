package watchlistarr.plex.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenWatchlistItem {
    public String title;
    public String guid;
    public String type;
    public String key;
    @JsonProperty("Guid")
    public List<Guid> Guid = List.of();

    public TokenWatchlistItem() {}
    public TokenWatchlistItem(String title, String guid, String type, String key) {
        this.title = title; this.guid = guid; this.type = type; this.key = key;
    }
}
