package io.github.albertsongs.videoreceiversmanager.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "playlist")
@Data
@NoArgsConstructor
public final class PlaylistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String youtubeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playlist")
    private List<VideoEntity> videos;
}
