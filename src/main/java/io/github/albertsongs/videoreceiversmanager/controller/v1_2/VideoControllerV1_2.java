package io.github.albertsongs.videoreceiversmanager.controller.v1_2;

import io.github.albertsongs.videoreceiversmanager.controller.v1_1.VideoControllerV1_1;
import io.github.albertsongs.videoreceiversmanager.service.VideoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1.2/videos")
public class VideoControllerV1_2 extends VideoControllerV1_1 {
    public VideoControllerV1_2(VideoService videoService) {
        super(videoService);
    }
}
