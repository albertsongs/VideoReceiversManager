package io.github.albertsongs.videoreceiversmanager.controller.v1_1;

import io.github.albertsongs.videoreceiversmanager.controller.v1.PlaylistControllerV1Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PlaylistControllerTestV11Test extends PlaylistControllerV1Test {
    final String BASE_URL = "/api/v1.1/playlists";

    protected String getBaseUrl() {
        return BASE_URL;
    }
}
