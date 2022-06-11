package io.github.albertsongs.videoreceiversmanager.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "playlist")
@Data
public final class PlaylistEntity {
    @Id
    private Long id;
    private String name;
    private String youtubeId;
}
