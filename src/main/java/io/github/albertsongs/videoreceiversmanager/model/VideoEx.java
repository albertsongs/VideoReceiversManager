package io.github.albertsongs.videoreceiversmanager.model;

import io.github.albertsongs.videoreceiversmanager.entity.VideoEntity;
import lombok.Getter;
import lombok.Setter;

public final class VideoEx extends Video {
    @Getter
    @Setter
    private VideoMeta youtube;
    public VideoEx(VideoEntity entity) {
        super(entity);
        youtube = new VideoMeta(entity.getYoutubeId(), entity.getPlaylist().getYoutubeId());
    }
}
