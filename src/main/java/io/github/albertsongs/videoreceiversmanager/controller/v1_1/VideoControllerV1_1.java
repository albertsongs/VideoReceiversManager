package io.github.albertsongs.videoreceiversmanager.controller.v1_1;

import io.github.albertsongs.videoreceiversmanager.controller.v1.VideoControllerV1;
import io.github.albertsongs.videoreceiversmanager.service.VideoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1.1/videos")
public class VideoControllerV1_1 extends VideoControllerV1 {
    public VideoControllerV1_1(VideoService videoService) {
        super(videoService);
    }
}
