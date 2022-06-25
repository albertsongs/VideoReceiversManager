package io.github.albertsongs.videoreceiversmanager.controller.v1;

import io.github.albertsongs.videoreceiversmanager.model.ObjectListContainer;
import io.github.albertsongs.videoreceiversmanager.model.Playlist;
import io.github.albertsongs.videoreceiversmanager.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/playlists")
@CrossOrigin("https://albertsongs.github.io")
public final class PlaylistController {
    @Autowired
    private PlaylistService playlistService;
    @GetMapping
    public ObjectListContainer<Playlist> getAllPlaylists() {
        ObjectListContainer<Playlist> list = new ObjectListContainer<>();
        list.setList(playlistService.getAll());
        return list;
    }
    @GetMapping("/{playlistId}")
    public Playlist getPlaylistById(@PathVariable(value = "playlistId") Long playlistId){
        return playlistService.getById(playlistId);
    }
}
