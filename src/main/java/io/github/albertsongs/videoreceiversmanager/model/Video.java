package io.github.albertsongs.videoreceiversmanager.model;

import io.github.albertsongs.videoreceiversmanager.entity.VideoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Video {
    private Long id;
    private String title;
    private String url;
    private String subtitlesUrl;
    public Video(VideoEntity entity){
        id = entity.getId();
        title = entity.getTitle();
        url = entity.getUrl();
        subtitlesUrl = entity.getSubtitlesUrl();
    }
}
