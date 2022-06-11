package io.github.albertsongs.videoreceiversmanager.model;

import io.github.albertsongs.videoreceiversmanager.entity.PlaylistEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Playlist {
    private Long id;
    private String name;
    private String youtubeId;

    public Playlist(PlaylistEntity entity){
        id = entity.getId();
        name = entity.getName();
        youtubeId = entity.getYoutubeId();
    }
    public PlaylistEntity toEntity(){
        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setId(id);
        playlistEntity.setName(name);
        playlistEntity.setYoutubeId(youtubeId);
        return playlistEntity;
    }
}
