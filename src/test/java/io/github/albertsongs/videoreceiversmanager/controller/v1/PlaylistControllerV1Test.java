package io.github.albertsongs.videoreceiversmanager.controller.v1;

import io.github.albertsongs.videoreceiversmanager.TestConfig;
import io.github.albertsongs.videoreceiversmanager.controller.AbstractControllerTest;
import io.github.albertsongs.videoreceiversmanager.exception.ApiError;
import io.github.albertsongs.videoreceiversmanager.exception.ObjectNotFound;
import io.github.albertsongs.videoreceiversmanager.model.ObjectListContainer;
import io.github.albertsongs.videoreceiversmanager.model.Playlist;
import io.github.albertsongs.videoreceiversmanager.service.PlaylistService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public
class PlaylistControllerV1Test extends AbstractControllerTest {
    @MockBean
    PlaylistService playlistService;
    final String BASE_URL = "/api/v1/playlists";

    protected String getBaseUrl() {
        return BASE_URL;
    }

    protected List<Playlist> buildPlaylists() {
        return new LinkedList<>() {{
            for (Integer i = 1; i <= TestConfig.TEST_ENTITY_COUNT; i++) {
                add(buildPlaylist(i.longValue()));
            }
        }};
    }

    protected Playlist buildPlaylist(Long Id) {
        String name = "Test video playlist" + Id;
        String youtubeId = "YoUTuBE0PlaYLisT2iD" + Id;
        return new Playlist(Id, name, youtubeId);
    }

    @Test
    void getAllPlaylists() throws Exception {
        final List<Playlist> playlists = buildPlaylists();
        Mockito.doReturn(playlists)
                .when(playlistService)
                .getAll();
        ObjectListContainer<Playlist> expectedVideosContainer = new ObjectListContainer<>(playlists);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(getBaseUrl())
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        final int status = mvcResult.getResponse().getStatus();
        final String responseJson = mvcResult.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), status);
        assertEquals(mapToJson(expectedVideosContainer), responseJson);
        Mockito.verify(playlistService, Mockito.times(1)).getAll();
    }

    @Test
    void getPlaylistById() throws Exception {
        final Long playlistId = 1L;
        final Playlist expectedPlaylist = buildPlaylist(playlistId);
        Mockito.doReturn(expectedPlaylist)
                .when(playlistService)
                .getById(playlistId);

        String uri = getBaseUrl() + "/" + playlistId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        final int status = mvcResult.getResponse().getStatus();
        final String responseJson = mvcResult.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), status);
        assertEquals(mapToJson(expectedPlaylist), responseJson);
        Mockito.verify(playlistService, Mockito.times(1)).getById(playlistId);
    }

    @Test
    void getPlaylistByIdNotFound() throws Exception {
        final Long playlistId = 1L;
        ObjectNotFound objectNotFound = new ObjectNotFound(Playlist.class.getSimpleName(), playlistId.toString());
        Mockito.doThrow(objectNotFound)
                .when(playlistService)
                .getById(playlistId);

        String uri = getBaseUrl() + "/" + playlistId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        final int status = mvcResult.getResponse().getStatus();
        final String responseJson = mvcResult.getResponse().getContentAsString();
        final ApiError responseError = mapFromJson(responseJson, ApiError.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), status);
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), responseError.getStatus());
        assertEquals(objectNotFound.getMessage(), responseError.getMessage());
        Mockito.verify(playlistService, Mockito.times(1)).getById(playlistId);
    }
}