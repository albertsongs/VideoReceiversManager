package io.github.albertsongs.videoreceiversmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YoutubeVideoInfo {
    String videoId;
    String playlistId;
}
