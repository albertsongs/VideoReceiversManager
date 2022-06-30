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
        final long playlist2Id = 1;
        final long playlist3Id = 2;
        List<VideoEntity> videoEntityList1 = buildTestVideoEntityList(TestConfig.TEST_ENTITY_COUNT/2, playlist1Id);
        List<VideoEntity> videoEntityList2 = buildTestVideoEntityList(TestConfig.TEST_ENTITY_COUNT/2, playlist2Id);
        List<VideoEntity> fullVideoEntityList = new LinkedList<>();
        fullVideoEntityList.addAll(videoEntityList1);
        fullVideoEntityList.addAll(videoEntityList2);
        Mockito.doReturn(fullVideoEntityList)
                .when(videoRepo)
                .findAll();
        List<Video> expectedVideos1 = new LinkedList<>();
        List<Video> expectedVideos2 = new LinkedList<>();
        videoEntityList1.forEach((videoEntity -> expectedVideos1.add(new Video(videoEntity))));
        videoEntityList2.forEach((videoEntity -> expectedVideos2.add(new Video(videoEntity))));

        assertEquals(expectedVideos1, videoService.getAllFromPlaylistById(playlist1Id));
        Mockito.verify(videoRepo, Mockito.times(1)).findAll();

        assertEquals(expectedVideos2, videoService.getAllFromPlaylistById(playlist2Id));
        Mockito.verify(videoRepo, Mockito.times(2)).findAll();

        assertEquals(new LinkedList<>(), videoService.getAllFromPlaylistById(playlist3Id));
        Mockito.verify(videoRepo, Mockito.times(3)).findAll();
    }

    @Test
    void getAll() {
        List<VideoEntity> videoEntityList = buildTestVideoEntityList(TestConfig.TEST_ENTITY_COUNT/2, 0);
        videoEntityList.addAll(buildTestVideoEntityList(TestConfig.TEST_ENTITY_COUNT/2, 1));
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
        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setId(id);
        playlistEntity.setName("Test playlist");
        playlistEntity.setYoutubeId("YoUTuBE0PlaYLisT2iD");
        return playlistEntity;
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