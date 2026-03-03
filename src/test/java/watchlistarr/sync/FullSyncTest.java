package watchlistarr.sync;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import watchlistarr.config.*;
import watchlistarr.model.Item;
import watchlistarr.plex.PlexService;
import watchlistarr.radarr.RadarrService;
import watchlistarr.sonarr.SonarrService;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FullSyncTest {

    @Mock ConfigurationService configService;
    @Mock PlexService plexService;
    @Mock SonarrService sonarrService;
    @Mock RadarrService radarrService;
    @InjectMocks FullSync fullSync;

    private AppConfig buildConfig() {
        var sonarr = new SonarrConfig("http://localhost:8989", "key", 1, "/shows", false, "all", 1, Set.of());
        var radarr = new RadarrConfig("http://localhost:7878", "key", 1, "/movies", false, Set.of());
        var plex   = new PlexConfig(Set.of(), Set.of("token"), false, true);
        var delete = new DeleteConfig(false, false, false, 7, true);
        return new AppConfig(60, sonarr, radarr, plex, delete);
    }

    @Test void sync_addsNewShow() {
        var config = buildConfig();
        var watchedShow = new Item("New Show", List.of("tvdb://999"), "show", null);
        when(plexService.getSelfWatchlist(any())).thenReturn(Set.of(watchedShow));
        when(plexService.getOthersWatchlist(any())).thenReturn(Set.of());
        when(sonarrService.fetchSeries(any(), eq(false))).thenReturn(Set.of());
        when(radarrService.fetchMovies(any(), eq(false))).thenReturn(Set.of());

        fullSync.sync(config, true);

        verify(sonarrService).addToSonarr(any(), eq(watchedShow));
        verify(radarrService, never()).addToRadarr(any(), any());
    }

    @Test void sync_addsNewMovie() {
        var config = buildConfig();
        var watchedMovie = new Item("New Movie", List.of("tmdb://888"), "movie", null);
        when(plexService.getSelfWatchlist(any())).thenReturn(Set.of(watchedMovie));
        when(plexService.getOthersWatchlist(any())).thenReturn(Set.of());
        when(sonarrService.fetchSeries(any(), eq(false))).thenReturn(Set.of());
        when(radarrService.fetchMovies(any(), eq(false))).thenReturn(Set.of());

        fullSync.sync(config, true);

        verify(radarrService).addToRadarr(any(), eq(watchedMovie));
        verify(sonarrService, never()).addToSonarr(any(), any());
    }

    @Test void sync_skipsExistingItem() {
        var config = buildConfig();
        var existing = new Item("Existing", List.of("tvdb://1"), "show", false);
        var watched  = new Item("Existing", List.of("tvdb://1"), "show", null);
        when(plexService.getSelfWatchlist(any())).thenReturn(Set.of(watched));
        when(plexService.getOthersWatchlist(any())).thenReturn(Set.of());
        when(sonarrService.fetchSeries(any(), eq(false))).thenReturn(Set.of(existing));
        when(radarrService.fetchMovies(any(), eq(false))).thenReturn(Set.of());

        fullSync.sync(config, true);

        verify(sonarrService, never()).addToSonarr(any(), any());
    }

    @Test void sync_rssOnly_doesNotCallTokenWatchlist() {
        var config = buildConfig();
        when(sonarrService.fetchSeries(any(), eq(false))).thenReturn(Set.of());
        when(radarrService.fetchMovies(any(), eq(false))).thenReturn(Set.of());

        fullSync.sync(config, false);

        verify(plexService, never()).getSelfWatchlist(any());
        verify(plexService, never()).getOthersWatchlist(any());
    }
}
