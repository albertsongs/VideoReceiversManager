package io.github.albertsongs.videoreceiversmanager.controller.v1;

import io.github.albertsongs.videoreceiversmanager.model.ObjectListContainer;
import io.github.albertsongs.videoreceiversmanager.model.Playlist;
import io.github.albertsongs.videoreceiversmanager.service.PlaylistService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/playlists")
@AllArgsConstructor
public class PlaylistControllerV1 {
    private final PlaylistService playlistService;

    @GetMapping
    public ObjectListContainer<Playlist> getAllPlaylists() {
        return new ObjectListContainer<>(playlistService.getAll());
    }

    @GetMapping("/{playlistId}")
    public Playlist getPlaylistById(@PathVariable(value = "playlistId") Long playlistId) {
        return playlistService.getById(playlistId);
    }
}
