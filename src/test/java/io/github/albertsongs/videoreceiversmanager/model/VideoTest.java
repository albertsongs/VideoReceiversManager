package io.github.albertsongs.videoreceiversmanager.model;

import io.github.albertsongs.videoreceiversmanager.entity.VideoEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VideoTest {
    @Test
    void testConstructor() {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setId(76532L);
        videoEntity.setTitle("Test video");
        videoEntity.setUrl("https://localhost:8080/content/videos/test.webm");
        videoEntity.setSubtitlesUrl("https://localhost:8080/content/subtitles/test.vtt");
        Video video = new Video(videoEntity);
        assertEquals(videoEntity.getId(), video.getId());
        assertEquals(videoEntity.getTitle(), video.getTitle());
        assertEquals(videoEntity.getUrl(), video.getUrl());
        assertEquals(videoEntity.getSubtitlesUrl(), video.getSubtitlesUrl());
    }
}
