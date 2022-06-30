package io.github.albertsongs.videoreceiversmanager.service;

import io.github.albertsongs.videoreceiversmanager.TestConfig;
import io.github.albertsongs.videoreceiversmanager.entity.PlaylistEntity;
import io.github.albertsongs.videoreceiversmanager.exception.ObjectNotFound;
import io.github.albertsongs.videoreceiversmanager.model.Playlist;
import io.github.albertsongs.videoreceiversmanager.repository.PlaylistRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlaylistServiceTest {
    @Autowired
    private  PlaylistService playlistService;
    @MockBean
    private PlaylistRepo playlistRepo;
    private long testPlaylistEntityCounter = 0;

    @Test
    void getAll() {
        List<PlaylistEntity> playlistEntityList = buildTestPlaylistEntityList();
        Mockito.doReturn(playlistEntityList)
                .when(playlistRepo)
                .findAll();
        List<Playlist> expectedPlaylists = new LinkedList<>();
        playlistEntityList.forEach((playlistEntity -> expectedPlaylists.add(new Playlist(playlistEntity))));

        assertEquals(expectedPlaylists, playlistService.getAll());
        Mockito.verify(playlistRepo, Mockito.times(1)).findAll();
    }

    @Test
    void getById() {
        PlaylistEntity expectedPlaylistEntity = buildTestPlaylistEntity();
        Mockito.doReturn(Optional.of(expectedPlaylistEntity))
                .when(playlistRepo)
                .findById(expectedPlaylistEntity.getId());
        assertEquals(new Playlist(expectedPlaylistEntity), playlistService.getById(expectedPlaylistEntity.getId()));
        Mockito.verify(playlistRepo, Mockito.times(1)).findById(expectedPlaylistEntity.getId());

        assertThrows(ObjectNotFound.class, () -> playlistService.getById(1000L));
    }

    PlaylistEntity buildTestPlaylistEntity() {
        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setId(testPlaylistEntityCounter);
        playlistEntity.setName("Test playlist " + testPlaylistEntityCounter++);
        playlistEntity.setYoutubeId("YoUTuBE0PlaYLisT2iD");
        return playlistEntity;
    }

    List<PlaylistEntity> buildTestPlaylistEntityList() {
        List<PlaylistEntity> entityList = new LinkedList<>();
        for (int i = 0; i < TestConfig.TEST_ENTITY_COUNT; i++) {
            entityList.add(buildTestPlaylistEntity());
        }

        return entityList;
    }
}