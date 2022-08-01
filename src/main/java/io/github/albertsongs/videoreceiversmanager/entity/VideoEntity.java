package io.github.albertsongs.videoreceiversmanager.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity(name = "video")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class VideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String youtubeId;
    @ManyToOne()
    @JoinColumn(name = "playlist_id")
    PlaylistEntity playlist;
    String url;
    String subtitlesUrl;
}
