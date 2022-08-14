package io.github.albertsongs.videoreceiversmanager.service;

import io.github.albertsongs.videoreceiversmanager.TestConfig;
import io.github.albertsongs.videoreceiversmanager.entity.PlaylistEntity;
import io.github.albertsongs.videoreceiversmanager.entity.VideoEntity;
import io.github.albertsongs.videoreceiversmanager.exception.ObjectNotFound;
import io.github.albertsongs.videoreceiversmanager.model.Video;
import io.github.albertsongs.videoreceiversmanager.repository.VideoRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class VideoServiceTest {
    @Autowired
    private VideoService videoService;

    @MockBean
    private VideoRepo videoRepo;

    private long testVideoEntityCounter = 1;

    @Test
    void getAllFromPlaylistById() {
        final long playlist1Id = 0;
        List<VideoEntity> videoEntityList1 = buildTestVideoEntityList(TestConfig.TEST_ENTITY_COUNT / 2, playlist1Id);

        Mockito.doReturn(videoEntityList1)
                .when(videoRepo)
                .findAll(Mockito.any(Example.class), Mockito.any(Sort.class));

        List<Video> expectedVideos1 = videoEntityList1.stream().map(Video::new).toList();

        assertEquals(expectedVideos1, videoService.getAllFromPlaylistById(playlist1Id));
        Mockito.verify(videoRepo, Mockito.times(1))
                .findAll(Mockito.any(Example.class), Mockito.any(Sort.class));
    }

    @Test
    void getAll() {
        List<VideoEntity> videoEntityList = buildTestVideoEntityList(TestConfig.TEST_ENTITY_COUNT / 2, 0);
        videoEntityList.addAll(buildTestVideoEntityList(TestConfig.TEST_ENTITY_COUNT / 2, 1));
        Mockito.doReturn(videoEntityList)
                .when(videoRepo)
                .findAll();
        List<Video> expectedVideos = new LinkedList<>();
        videoEntityList.forEach((videoEntity -> expectedVideos.add(new Video(videoEntity))));

        assertEquals(expectedVideos, videoService.getAll());
        Mockito.verify(videoRepo, Mockito.times(1)).findAll();
    }

    @Test
    void getVideoExById() {
        PlaylistEntity playlistEntity = buildTestPlaylistEntity(0L);
        VideoEntity expectedVideoEntity = buildTestVideoEntity(playlistEntity);
        Mockito.doReturn(Optional.of(expectedVideoEntity))
                .when(videoRepo)
                .findById(expectedVideoEntity.getId());
        assertEquals(new Video(expectedVideoEntity), videoService.getVideoExById(expectedVideoEntity.getId()));
        Mockito.verify(videoRepo, Mockito.times(1)).findById(expectedVideoEntity.getId());

        assertThrows(ObjectNotFound.class, () -> videoService.getVideoExById(1000L));
    }

    VideoEntity buildTestVideoEntity(PlaylistEntity playlistEntity) {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setId(testVideoEntityCounter);
        videoEntity.setTitle("Test video " + testVideoEntityCounter++);
        videoEntity.setYoutubeId("YoUTuBE2iD");
        videoEntity.setPlaylist(playlistEntity);
        return videoEntity;
    }

    PlaylistEntity buildTestPlaylistEntity(long id) {
        return PlaylistEntity.builder()
                .id(id)
                .name("Test playlist")
                .youtubeId("YoUTuBE0PlaYLisT2iD")
                .build();
    }

    List<VideoEntity> buildTestVideoEntityList(long count, long playlistId) {
        PlaylistEntity playlistEntity = buildTestPlaylistEntity(playlistId);
        List<VideoEntity> entityList = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            entityList.add(buildTestVideoEntity(playlistEntity));
        }

        return entityList;
    }
}