package io.github.albertsongs.videoreceiversmanager.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity(name = "playlist")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class PlaylistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String youtubeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playlist", fetch = FetchType.LAZY)
    List<VideoEntity> videos;
}
