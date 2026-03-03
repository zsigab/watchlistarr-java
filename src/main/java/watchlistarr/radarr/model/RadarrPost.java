package watchlistarr.radarr.model;
import java.util.List;

public class RadarrPost {
    public String title;
    public long tmdbId;
    public int qualityProfileId;
    public String rootFolderPath;
    public AddOptions addOptions = new AddOptions();
    public List<Integer> tags;

    public RadarrPost(String title, long tmdbId, int qualityProfileId, String rootFolderPath, List<Integer> tags) {
        this.title = title;
        this.tmdbId = tmdbId;
        this.qualityProfileId = qualityProfileId;
        this.rootFolderPath = rootFolderPath;
        this.tags = tags;
    }
}
