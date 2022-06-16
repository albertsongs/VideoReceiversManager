package io.github.albertsongs.videoreceiversmanager.model;

import io.github.albertsongs.videoreceiversmanager.entity.VideoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class Video {
    private Long id;
    private String title;
    private String youtubeId;
    private String youtubePlaylistId;

    public Video(VideoEntity entity){
        id = entity.getId();
        title = entity.getTitle();
        youtubeId = entity.getYoutubeId();
        youtubePlaylistId = entity.getPlaylist().getYoutubeId();
    }
}
