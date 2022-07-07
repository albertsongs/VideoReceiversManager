package io.github.albertsongs.videoreceiversmanager.controller.v1;

import io.github.albertsongs.videoreceiversmanager.TestConfig;
import io.github.albertsongs.videoreceiversmanager.controller.AbstractControllerTest;
import io.github.albertsongs.videoreceiversmanager.model.ObjectListContainer;
import io.github.albertsongs.videoreceiversmanager.model.Video;
import io.github.albertsongs.videoreceiversmanager.service.VideoService;
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
class VideoControllerV1Test extends AbstractControllerTest {
    @MockBean
    VideoService videoService;
    final String BASE_URL = "/api/v1/videos";

    protected List<Video> buildVideos() {
        return new LinkedList<>() {{
            for (Integer i = 1; i <= TestConfig.TEST_ENTITY_COUNT; i++) {
                add(buildVideo(i.longValue()));
            }
        }};
    }

    protected Video buildVideo(Long Id) {
        final String urlTemplate = "https://albertsongs.github.io/content/videos/%name%.webm";
        final String subtitlesUrlTemplate = "https://albertsongs.github.io/content/subtitles/%name%.vtt";
        String url = urlTemplate.replace("%name%", Id.toString());
        String subtitlesUrl = subtitlesUrlTemplate.replace("%name%", Id.toString());
        return new Video(Id, "Video", url, subtitlesUrl);
    }

    @Test
    void getAllVideos() throws Exception {
        List<Video> videos = buildVideos();
        Mockito.doReturn(videos)
                .when(videoService)
                .getAll();
        ObjectListContainer<Video> expectedVideosContainer = new ObjectListContainer<>(videos);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        final int status = mvcResult.getResponse().getStatus();
        final String responseJson = mvcResult.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), status);
        assertEquals(mapToJson(expectedVideosContainer), responseJson);
        Mockito.verify(videoService, Mockito.times(1)).getAll();
    }

    @Test
    void getAllVideosFromPlaylist() throws Exception {
        List<Video> videos = buildVideos();
        Long playlistId = 1L;
        Mockito.doReturn(videos)
                .when(videoService)
                .getAllFromPlaylistById(playlistId);
        ObjectListContainer<Video> expectedVideosContainer = new ObjectListContainer<>(videos);

        String uri = BASE_URL + "?playlistId=" + playlistId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        final int status = mvcResult.getResponse().getStatus();
        final String responseJson = mvcResult.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), status);
        assertEquals(mapToJson(expectedVideosContainer), responseJson);
        Mockito.verify(videoService, Mockito.times(1)).getAllFromPlaylistById(playlistId);
    }
}