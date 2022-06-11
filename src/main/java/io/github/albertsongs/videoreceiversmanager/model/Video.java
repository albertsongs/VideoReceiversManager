package io.github.albertsongs.videoreceiversmanager.model;

import io.github.albertsongs.videoreceiversmanager.entity.VideoEntity;
import lombok.Data;

@Data
public class Video {
    private Long id;
    private String title;
    private String youtubeId;

    public Video(VideoEntity entity){
        id = entity.getId();
        title = entity.getTitle();
        youtubeId = entity.getYoutubeId();
    }
}
