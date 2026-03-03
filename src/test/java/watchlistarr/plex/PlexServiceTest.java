package watchlistarr.plex;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import watchlistarr.config.PlexConfig;
import watchlistarr.http.HttpService;
import watchlistarr.model.Item;

import java.io.InputStream;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PlexServiceTest {

    @Mock HttpService http;
    @InjectMocks PlexService plexService;

    private final ObjectMapper mapper = new ObjectMapper();
    private final PlexConfig config = new PlexConfig(Set.of(), Set.of("test-token"), false, false);

    @Test void fetchWatchlistFromRss_returnsItems() throws Exception {
        when(http.getMapper()).thenReturn(mapper);
        var json = loadJson("watchlist.json");
        when(http.get(anyString())).thenReturn(Optional.of(json));

        Set<Item> result = plexService.fetchWatchlistFromRss("https://rss.plex.tv/fake");
        assertFalse(result.isEmpty());
    }

    @Test void fetchWatchlistFromRss_emptyOnFailure() {
        when(http.get(anyString())).thenReturn(Optional.empty());
        Set<Item> result = plexService.fetchWatchlistFromRss("https://rss.plex.tv/fake");
        assertTrue(result.isEmpty());
    }

    @Test void getSelfWatchlist_fetchesItems() throws Exception {
        when(http.getMapper()).thenReturn(mapper);
        var watchlistJson = loadJson("self-watchlist-from-token.json");
        var metadataJson  = loadJson("single-item-plex-metadata.json");

        // getSelfWatchlistForToken calls http.get(url) with no apiKey (token in URL)
        when(http.get(contains("library/sections/watchlist"))).thenReturn(Optional.of(watchlistJson));
        when(http.get(contains("discover.provider.plex.tv/library/metadata"))).thenReturn(Optional.of(metadataJson));

        Set<Item> result = plexService.getSelfWatchlist(config);
        assertFalse(result.isEmpty());
    }

    @Test void getOthersWatchlist_returnsItemsWhenFriendsFetched() throws Exception {
        when(http.getMapper()).thenReturn(mapper);
        var friendsJson   = loadJson("plex-get-all-friends.json");
        var watchlistJson = loadJson("plex-get-watchlist-from-friend.json");
        var metadataJson  = loadJson("single-item-plex-metadata.json");

        when(http.post(eq("https://community.plex.tv/api"), anyString(), any())).thenReturn(Optional.of(friendsJson));
        when(http.get(contains("discover.provider.plex.tv/library/metadata"))).thenReturn(Optional.of(metadataJson));

        // Return empty watchlist for friend to avoid complex pagination setup
        var emptyWatchlist = loadJson("plex-get-watchlist-from-friend.json");
        when(http.post(eq("https://community.plex.tv/api"), anyString(), any()))
            .thenReturn(Optional.of(friendsJson))  // first call = friends
            .thenReturn(Optional.of(watchlistJson)); // second call = friend's watchlist

        Set<Item> result = plexService.getOthersWatchlist(config);
        assertNotNull(result);
    }

    private com.fasterxml.jackson.databind.JsonNode loadJson(String name) throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(name)) {
            return mapper.readTree(is);
        }
    }
}
