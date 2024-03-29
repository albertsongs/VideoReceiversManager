package io.github.albertsongs.videoreceiversmanager.model;

import io.github.albertsongs.videoreceiversmanager.entity.PlaylistEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaylistTest {
    @Test
    void testToEntity() {
        Playlist playlist = new Playlist(
                12342L,
                "Test playlist",
                "fh378fy389fu9dio2suf3iou"
        );
        PlaylistEntity playlistEntity = playlist.toEntity();
        assertEquals(playlist.getId(), playlistEntity.getId());
        assertEquals(playlist.getName(), playlistEntity.getName());
        assertEquals(playlist.getYoutubeId(), playlistEntity.getYoutubeId());
    }

    @Test
    void testConstructor() {
        PlaylistEntity playlistEntity = PlaylistEntity.builder()
                .id(1234232L)
                .name("Test playlist")
                .youtubeId("fh378fy389fu9dio2suf3iou")
                .build();
        Playlist playlist = new Playlist(playlistEntity);
        assertEquals(playlistEntity.getId(), playlist.getId());
        assertEquals(playlistEntity.getName(), playlist.getName());
        assertEquals(playlistEntity.getYoutubeId(), playlist.getYoutubeId());
    }
}
