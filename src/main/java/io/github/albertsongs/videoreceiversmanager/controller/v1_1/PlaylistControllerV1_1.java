package io.github.albertsongs.videoreceiversmanager.controller.v1_1;

import io.github.albertsongs.videoreceiversmanager.controller.v1.PlaylistControllerV1;
import io.github.albertsongs.videoreceiversmanager.service.PlaylistService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1.1/playlists")
public class PlaylistControllerV1_1 extends PlaylistControllerV1 {
    public PlaylistControllerV1_1(PlaylistService playlistService) {
        super(playlistService);
    }
}
