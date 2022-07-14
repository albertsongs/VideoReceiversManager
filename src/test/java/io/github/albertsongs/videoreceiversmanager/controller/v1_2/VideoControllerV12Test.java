package io.github.albertsongs.videoreceiversmanager.controller.v1_2;

import io.github.albertsongs.videoreceiversmanager.controller.v1_1.VideoControllerV11Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VideoControllerV12Test extends VideoControllerV11Test {
    final String BASE_URL = "/api/v1.2/videos";

    protected String getBaseUrl() {
        return BASE_URL;
    }
}
