package io.github.albertsongs.videoreceiversmanager.service;

import io.github.albertsongs.videoreceiversmanager.entity.PlaylistEntity;
import io.github.albertsongs.videoreceiversmanager.entity.VideoEntity;
import io.github.albertsongs.videoreceiversmanager.exception.ObjectNotFound;
import io.github.albertsongs.videoreceiversmanager.model.Video;
import io.github.albertsongs.videoreceiversmanager.model.VideoEx;
import io.github.albertsongs.videoreceiversmanager.repository.VideoRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoRepo videoRepo;

    @Deprecated
    public Iterable<Video> getAllFromPlaylistById(Long playlistId) {
        final VideoEntity exemplaryVideoEntity = new VideoEntity();
        exemplaryVideoEntity.setPlaylist(PlaylistEntity.builder().id(playlistId).build());
        return videoRepo.findAll(Example.of(exemplaryVideoEntity), Sort.by(Sort.Order.asc("id")))
                .stream()
                .map(Video::new)
                .toList();
    }

    protected Iterable<Video> getAll(Predicate<VideoEntity> filter) {
        List<Video> videos = new LinkedList<>();
        videoRepo.findAll().forEach(videoEntity -> {
            if (filter.test(videoEntity)) {
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
