package watchlistarr.radarr.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RadarrMovieExclusion {
    public String movieTitle;
    public String imdbId;
    public Long tmdbId;
    public long id;
    public RadarrMovieExclusion() {}
}
