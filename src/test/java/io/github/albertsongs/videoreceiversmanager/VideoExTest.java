package io.github.albertsongs.videoreceiversmanager;

import io.github.albertsongs.videoreceiversmanager.entity.PlaylistEntity;
import io.github.albertsongs.videoreceiversmanager.entity.VideoEntity;
import io.github.albertsongs.videoreceiversmanager.model.VideoEx;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VideoExTest {
    @Test
    void testConstructor(){
        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setId(1234232L);
        playlistEntity.setName("Test playlist");

        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setId(76532L);
        videoEntity.setTitle("Test video");
        videoEntity.setYoutubeId("FH378fs389FU");
        videoEntity.setPlaylist(playlistEntity);
        videoEntity.setUrl("https://localhost:8080/videos/1.webm");
        videoEntity.setSubtitlesUrl("https://localhost:8080/subtitles/1.vtt");
        VideoEx video = new VideoEx(videoEntity);

        assertEquals(videoEntity.getId(),video.getId());
        assertEquals(videoEntity.getTitle(), video.getTitle());
        assertEquals(videoEntity.getYoutubeId(), video.getYoutube().getVideoId());
        assertEquals(videoEntity.getPlaylist().getYoutubeId(), video.getYoutube().getPlaylistId());
        assertEquals(videoEntity.getUrl(), video.getUrl());
        assertEquals(videoEntity.getSubtitlesUrl(), video.getSubtitlesUrl());
    }
}
