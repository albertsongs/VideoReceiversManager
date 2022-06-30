package io.github.albertsongs.videoreceiversmanager.service;

import io.github.albertsongs.videoreceiversmanager.entity.VideoEntity;
import io.github.albertsongs.videoreceiversmanager.exception.ObjectNotFound;
import io.github.albertsongs.videoreceiversmanager.model.VideoEx;
import io.github.albertsongs.videoreceiversmanager.model.Video;
import io.github.albertsongs.videoreceiversmanager.repository.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public final class VideoService {
    @Autowired
    private VideoRepo videoRepo;

    //TODO: replace to getAll(filter, sorter)
    public Iterable<Video> getAllFromPlaylistById(Long playlistId) {
        List<Video> videos = new LinkedList<>();
        videoRepo.findAll().forEach(videoEntity -> {
            if (videoEntity.getPlaylist().getId().equals(playlistId)) {
                videos.add(new Video(videoEntity));
            }
        });
        return videos;
    }

    public Iterable<Video> getAll() {
        List<Video> videos = new LinkedList<>();
        videoRepo.findAll().forEach(videoEntity -> videos.add(new Video(videoEntity)));
        return videos;
    }

    public VideoEx getVideoExById(Long id) {
        VideoEntity videoEntity = videoRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFound(Video.class.getSimpleName(), id.toString()));
        return new VideoEx(videoEntity);
    }
}
