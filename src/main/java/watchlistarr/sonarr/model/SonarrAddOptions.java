package watchlistarr.sonarr.model;

public class SonarrAddOptions {
    public String monitor;
    public boolean searchForCutoffUnmetEpisodes = true;
    public boolean searchForMissingEpisodes = true;
    public SonarrAddOptions(String monitor) { this.monitor = monitor; }
}
