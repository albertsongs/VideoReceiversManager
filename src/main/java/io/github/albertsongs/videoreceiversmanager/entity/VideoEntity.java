package io.github.albertsongs.videoreceiversmanager.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "video")
@Data
public class VideoEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String youtubeId;
    @ManyToOne()
    @JoinColumn(name = "playlist_id")
    private PlaylistEntity playlist;
}
