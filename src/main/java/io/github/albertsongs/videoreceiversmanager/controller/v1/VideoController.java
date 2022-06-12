package io.github.albertsongs.videoreceiversmanager.controller.v1;

import io.github.albertsongs.videoreceiversmanager.model.ObjectListContainer;
import io.github.albertsongs.videoreceiversmanager.model.Video;
import io.github.albertsongs.videoreceiversmanager.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/videos")
public final class VideoController {
    @Autowired
    private VideoService videoService;
    @GetMapping
    public ObjectListContainer<Video> getAllVideos(@RequestParam(name="playlistId", required = false) Long playlistId) {
        final ObjectListContainer<Video> videos = new ObjectListContainer<>();
        videos.setList(playlistId == null
                ? videoService.getAll()
                : videoService.getAllFromPlaylistById(playlistId));
        return videos;
    }
}
