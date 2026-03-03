package watchlistarr.plex.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RssInfo {
    public String url;
    public RssInfo() {}
}
