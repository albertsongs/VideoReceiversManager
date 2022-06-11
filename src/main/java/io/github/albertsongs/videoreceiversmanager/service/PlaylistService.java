package io.github.albertsongs.videoreceiversmanager.service;

import io.github.albertsongs.videoreceiversmanager.entity.PlaylistEntity;
import io.github.albertsongs.videoreceiversmanager.model.Playlist;
import io.github.albertsongs.videoreceiversmanager.repository.PlaylistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public final class PlaylistService {
    @Autowired
    private PlaylistRepo playlistRepo;

    public Iterable<Playlist> getAll() {
        List<Playlist> playlists = new LinkedList<>();
        playlistRepo.findAll().forEach(entity -> playlists.add(new Playlist(entity)));
        return playlists;
    }

    public Optional<Playlist> getById(Long playlistId) {
        Optional<PlaylistEntity> playlistEntity = playlistRepo.findById(playlistId);
        return playlistEntity.isEmpty()
                ? Optional.empty()
                : Optional.of(new Playlist(playlistEntity.get()));
    }
}
