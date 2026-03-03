package watchlistarr.sonarr.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SonarrSeries {
    public String title;
    public String imdbId;
    public Long tvdbId;
    public long id;
    public Boolean ended;
    public SonarrSeries() {}
}
