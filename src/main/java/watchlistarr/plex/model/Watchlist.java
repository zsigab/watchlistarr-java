package watchlistarr.plex.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import watchlistarr.model.Item;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Watchlist {
    public Set<Item> items = Set.of();
    public Watchlist() {}
}
