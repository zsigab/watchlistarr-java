package watchlistarr.radarr.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RadarrMovie {
    public String title;
    public String imdbId;
    public Long tmdbId;
    public long id;
    public RadarrMovie() {}
}
