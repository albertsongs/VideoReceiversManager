package io.github.albertsongs.videoreceiversmanager.service;

import io.github.albertsongs.videoreceiversmanager.model.Video;
import io.github.albertsongs.videoreceiversmanager.model.VideoEx;
import org.springframework.stereotype.Service;

@Service
public interface VideoService {
    Iterable<Video> getAllFromPlaylistById(Long playlistId);

    Iterable<Video> getAll();

    VideoEx getVideoExById(Long id);
}
