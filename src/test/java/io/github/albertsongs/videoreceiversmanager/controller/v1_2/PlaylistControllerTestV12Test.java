package io.github.albertsongs.videoreceiversmanager.controller.v1_2;

import io.github.albertsongs.videoreceiversmanager.controller.v1_1.PlaylistControllerTestV11Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PlaylistControllerTestV12Test extends PlaylistControllerTestV11Test {
    final String BASE_URL = "/api/v1.2/playlists";

    protected String getBaseUrl() {
        return BASE_URL;
    }
}
