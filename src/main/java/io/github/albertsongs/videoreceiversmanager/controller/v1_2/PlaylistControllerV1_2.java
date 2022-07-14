package io.github.albertsongs.videoreceiversmanager.controller.v1_2;

import io.github.albertsongs.videoreceiversmanager.controller.v1_1.PlaylistControllerV1_1;
import io.github.albertsongs.videoreceiversmanager.service.PlaylistService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1.2/playlists")
public class PlaylistControllerV1_2 extends PlaylistControllerV1_1 {
    public PlaylistControllerV1_2(PlaylistService playlistService) {
        super(playlistService);
    }
}
