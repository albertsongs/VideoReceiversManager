package io.github.albertsongs.videoreceiversmanager.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "playlist")
@Data
public final class PlaylistEntity {
    @Id
    private Long id;
    private String name;
    private String youtubeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playlist")
    private List<VideoEntity> videos;
}
