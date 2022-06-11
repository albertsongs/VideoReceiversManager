package io.github.albertsongs.videoreceiversmanager.controller.v1;

import io.github.albertsongs.videoreceiversmanager.exception.ObjectNotFound;
import io.github.albertsongs.videoreceiversmanager.model.ObjectList;
import io.github.albertsongs.videoreceiversmanager.model.Playlist;
import io.github.albertsongs.videoreceiversmanager.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/playlists")
public final class PlaylistController {
    @Autowired
    private PlaylistService playlistService;
    @GetMapping
    public ObjectList<Playlist> getAllPlaylists() {
        ObjectList<Playlist> list = new ObjectList<>();
        list.setList(playlistService.getAll());
        return list;
    }
    @GetMapping("/{playlistId}")
    public Playlist getPlaylistById(@PathVariable(value = "playlistId") Long playlistId){
        return playlistService.getById(playlistId)
                .orElseThrow(()->new ObjectNotFound(Playlist.class.getSimpleName(), playlistId.toString()));
    }
}
