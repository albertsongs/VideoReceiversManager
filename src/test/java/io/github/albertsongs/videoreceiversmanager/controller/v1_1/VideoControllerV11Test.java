package io.github.albertsongs.videoreceiversmanager.controller.v1_1;

import io.github.albertsongs.videoreceiversmanager.controller.v1.VideoControllerV1Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VideoControllerV11Test extends VideoControllerV1Test {
    final String BASE_URL = "/api/v1.1/videos";

    protected String getBaseUrl() {
        return BASE_URL;
    }
}
