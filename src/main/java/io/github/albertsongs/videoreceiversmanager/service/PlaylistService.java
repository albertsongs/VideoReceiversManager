package io.github.albertsongs.videoreceiversmanager.service;

import io.github.albertsongs.videoreceiversmanager.model.Playlist;
import org.springframework.stereotype.Service;

@Service
public interface PlaylistService {
    Iterable<Playlist> getAll();

    Playlist getById(Long playlistId);
}
