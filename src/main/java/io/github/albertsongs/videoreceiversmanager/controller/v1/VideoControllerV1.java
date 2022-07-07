package io.github.albertsongs.videoreceiversmanager.controller.v1;

import io.github.albertsongs.videoreceiversmanager.model.ObjectListContainer;
import io.github.albertsongs.videoreceiversmanager.model.Video;
import io.github.albertsongs.videoreceiversmanager.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/videos")
@CrossOrigin("https://albertsongs.github.io")
public final class VideoControllerV1 {
    @Autowired
    private VideoService videoService;

    @GetMapping
    public ObjectListContainer<Video> getAllVideos(
            @RequestParam(name = "playlistId", required = false) Long playlistId) {
        return new ObjectListContainer<>(playlistId == null
                ? videoService.getAll()
                : videoService.getAllFromPlaylistById(playlistId));
    }
}
