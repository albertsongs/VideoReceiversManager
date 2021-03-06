package io.github.albertsongs.videoreceiversmanager.service;

import io.github.albertsongs.videoreceiversmanager.entity.PlaylistEntity;
import io.github.albertsongs.videoreceiversmanager.exception.ObjectNotFound;
import io.github.albertsongs.videoreceiversmanager.model.Playlist;
import io.github.albertsongs.videoreceiversmanager.repository.PlaylistRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public final class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepo playlistRepo;

    public Iterable<Playlist> getAll() {
        List<Playlist> playlists = new LinkedList<>();
        playlistRepo.findAll().forEach(entity -> playlists.add(new Playlist(entity)));
        return playlists;
    }

    public Playlist getById(Long playlistId) {
        PlaylistEntity playlistEntity = playlistRepo.findById(playlistId)
                .orElseThrow(() -> new ObjectNotFound(Playlist.class.getSimpleName(), playlistId.toString()));
        return new Playlist(playlistEntity);
    }
}
