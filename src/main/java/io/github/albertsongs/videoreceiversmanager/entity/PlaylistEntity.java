package io.github.albertsongs.videoreceiversmanager.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "playlist")
@Data
public final class PlaylistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String youtubeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playlist")
    private List<VideoEntity> videos;
}
