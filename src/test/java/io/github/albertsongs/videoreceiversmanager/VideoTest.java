package io.github.albertsongs.videoreceiversmanager;

import io.github.albertsongs.videoreceiversmanager.entity.VideoEntity;
import io.github.albertsongs.videoreceiversmanager.model.Video;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VideoTest {
    @Test
    void testConstructor(){
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setId(76532L);
        videoEntity.setTitle("Test video");
        videoEntity.setYoutubeId("FH378fs389FU");
        Video video = new Video(videoEntity);
        assertEquals(videoEntity.getId(),video.getId());
        assertEquals(videoEntity.getTitle(), video.getTitle());
    }
}
