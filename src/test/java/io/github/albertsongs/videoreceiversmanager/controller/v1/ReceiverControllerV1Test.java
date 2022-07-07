package io.github.albertsongs.videoreceiversmanager.controller.v1;

import io.github.albertsongs.videoreceiversmanager.TestConfig;
import io.github.albertsongs.videoreceiversmanager.controller.AbstractControllerTest;
import io.github.albertsongs.videoreceiversmanager.controller.MessageController;
import io.github.albertsongs.videoreceiversmanager.exception.ApiError;
import io.github.albertsongs.videoreceiversmanager.exception.ObjectNotFound;
import io.github.albertsongs.videoreceiversmanager.exception.ReceiverIdInvalidFormat;
import io.github.albertsongs.videoreceiversmanager.model.*;
import io.github.albertsongs.videoreceiversmanager.service.ReceiverService;
import io.github.albertsongs.videoreceiversmanager.service.VideoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
public
class ReceiverControllerV1Test extends AbstractControllerTest {
    @MockBean
    protected ReceiverService receiverService;
    @MockBean
    protected VideoService videoService;
    @MockBean
    protected MessageController messageController;
    final String BASE_URL = "/api/v1/receivers";

    protected String getBaseUrl() {
        return BASE_URL;
    }

    protected List<Receiver> buildReceivers() {
        return new LinkedList<>() {{
            for (Integer i = 1; i <= TestConfig.TEST_ENTITY_COUNT; i++) {
                add(buildReceiver(UUID.randomUUID()));
            }
        }};
    }

    protected Receiver buildReceiver(UUID Id) {
        return new Receiver(Id, "Receiver " + Id, "127.0.0.1", new Date());
    }

    @Test
    void createReceiver() throws Exception {
        final UUID receiverId = UUID.randomUUID();
        final Receiver expectedReceiver = buildReceiver(receiverId);
        final String content = mapToJson(expectedReceiver);
        Mockito.doReturn(expectedReceiver)
                .when(receiverService)
                .add(any());

        String uri = getBaseUrl();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        final int status = mvcResult.getResponse().getStatus();
        final String responseJson = mvcResult.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), status);
        assertEquals(mapToJson(expectedReceiver), responseJson);
        Mockito.verify(receiverService, Mockito.times(1)).add(any());
    }

    @Test
    void getAllowedReceivers() throws Exception {
        final List<Receiver> receivers = buildReceivers();
        final String clientIpAddress = "127.0.0.1";
        Mockito.doReturn(receivers)
                .when(receiverService)
                .getAllWithLastIp(clientIpAddress);
        receivers.sort((r1, r2) -> r2.getUpdatedAt().compareTo(r1.getUpdatedAt()));
        ObjectListContainer<Receiver> expectedVideosContainer = new ObjectListContainer<>(receivers);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        final int status = mvcResult.getResponse().getStatus();
        final String responseJson = mvcResult.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), status);
        assertEquals(mapToJson(expectedVideosContainer), responseJson);
        Mockito.verify(receiverService, Mockito.times(1)).getAllWithLastIp(clientIpAddress);
    }

    @Test
    void getAllowedReceiversEmpty() throws Exception {
        final List<Receiver> receivers = new LinkedList<>();
        final String clientIpAddress = "127.0.0.1";
        Mockito.doReturn(receivers)
                .when(receiverService)
                .getAllWithLastIp(clientIpAddress);
        ObjectListContainer<Receiver> expectedVideosContainer = new ObjectListContainer<>(new LinkedList<>());

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        final int status = mvcResult.getResponse().getStatus();
        final String responseJson = mvcResult.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), status);
        assertEquals(mapToJson(expectedVideosContainer), responseJson);
        Mockito.verify(receiverService, Mockito.times(1)).getAllWithLastIp(clientIpAddress);
    }

    @Test
    void getReceiverById() throws Exception {
        final UUID receiverId = UUID.randomUUID();
        final Receiver expectedReceiver = buildReceiver(receiverId);
        Mockito.doReturn(expectedReceiver)
                .when(receiverService)
                .getById(receiverId.toString());

        String uri = getBaseUrl() + "/" + receiverId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        final int status = mvcResult.getResponse().getStatus();
        final String responseJson = mvcResult.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), status);
        assertEquals(mapToJson(expectedReceiver), responseJson);
        Mockito.verify(receiverService, Mockito.times(1)).getById(receiverId.toString());
    }

    @Test
    void getReceiverByBadId() throws Exception {
        final String receiverId = "not uuid";
        try {
            UUID.fromString(receiverId);
        } catch (IllegalArgumentException e) {
            ReceiverIdInvalidFormat receiverIdInvalidFormat = new ReceiverIdInvalidFormat(e.getMessage());
            Mockito.doThrow(receiverIdInvalidFormat)
                    .when(receiverService)
                    .getById(receiverId);
            String uri = getBaseUrl() + "/" + receiverId;
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
            final int status = mvcResult.getResponse().getStatus();
            final String responseJson = mvcResult.getResponse().getContentAsString();
            final ApiError responseError = mapFromJson(responseJson, ApiError.class);
            assertEquals(HttpStatus.BAD_REQUEST.value(), status);
            assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), responseError.getStatus());
            assertEquals(receiverIdInvalidFormat.getMessage(), responseError.getMessage());
            Mockito.verify(receiverService, Mockito.times(1)).getById(receiverId);
        }
    }

    @Test
    void getReceiverByIdNotFound() throws Exception {
        final UUID receiverId = UUID.randomUUID();
        ObjectNotFound objectNotFound = new ObjectNotFound(Receiver.class.getSimpleName(), receiverId.toString());
        Mockito.doThrow(objectNotFound)
                .when(receiverService)
                .getById(receiverId.toString());

        String uri = getBaseUrl() + "/" + receiverId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertsNotFound(mvcResult, objectNotFound);
        Mockito.verify(receiverService, Mockito.times(1)).getById(receiverId.toString());
    }

    @Test
    void patchReceiver() throws Exception {
        final UUID receiverId = UUID.randomUUID();
        final Receiver expectedReceiver = buildReceiver(receiverId);
        final String content = mapToJson(expectedReceiver);
        Mockito.doReturn(expectedReceiver)
                .when(receiverService)
                .update(eq(receiverId.toString()), any());

        String uri = getBaseUrl() + "/" + receiverId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.patch(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        final int status = mvcResult.getResponse().getStatus();
        final String responseJson = mvcResult.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), status);
        assertEquals(mapToJson(expectedReceiver), responseJson);
        Mockito.verify(receiverService, Mockito.times(1))
                .update(eq(receiverId.toString()), any());
    }

    @Test
    void deleteReceiverById() throws Exception {
        final UUID receiverId = UUID.randomUUID();

        String uri = getBaseUrl() + "/" + receiverId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        final int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
        Mockito.verify(receiverService, Mockito.times(1)).deleteById(receiverId.toString());
    }

    @Test
    void deleteReceiverByIdNotFound() throws Exception {
        final UUID receiverId = UUID.randomUUID();
        ObjectNotFound objectNotFound = new ObjectNotFound(Receiver.class.getSimpleName(), receiverId.toString());
        Mockito.doThrow(objectNotFound)
                .when(receiverService)
                .deleteById(receiverId.toString());

        String uri = getBaseUrl() + "/" + receiverId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertsNotFound(mvcResult, objectNotFound);
        Mockito.verify(receiverService, Mockito.times(1)).deleteById(receiverId.toString());
    }

    @Test
    void playVideoOnReceiverById() throws Exception {
        sendCommandTest("/playVideo");
    }

    @Test
    void playVideoOnReceiverByIdNotFoundVideo() throws Exception {
        sendCommandTestNotFoundVideo("/playVideo");
    }

    @Test
    void playVideoOnReceiverByIdNotFoundReceiver() throws Exception {
        sendCommandTestNotFoundReceiver("/playVideo");
    }

    /**
     * @param command example "/playVideo"
     */
    protected void sendCommandTest(String command) throws Exception {
        final UUID receiverId = UUID.randomUUID();
        final Long videoId = 1L;
        final VideoEx video = new VideoEx(videoId,
                "test", "http://localhost/test.webm",
                "http://localhost/test.vtt", new VideoMeta("vIdeOiD", "PlAylIsTiD"));
        final Receiver expectedReceiver = buildReceiver(receiverId);
        Mockito.doReturn(expectedReceiver)
                .when(receiverService)
                .getById(receiverId.toString());
        Mockito.doReturn(video)
                .when(videoService)
                .getVideoExById(videoId);

        String uri = getBaseUrl() + "/" + receiverId + command;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"id\":" + videoId + "}")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        final int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.ACCEPTED.value(), status);
        Mockito.verify(receiverService, Mockito.times(1)).getById(receiverId.toString());
    }

    /**
     * @param command example "/playVideo"
     */
    protected void sendCommandTestNotFoundVideo(String command) throws Exception {
        final UUID receiverId = UUID.randomUUID();
        final Long videoId = 1L;
        final Receiver expectedReceiver = buildReceiver(receiverId);
        final ObjectNotFound expectedExtension = new ObjectNotFound(Video.class.getSimpleName(), videoId.toString());
        Mockito.doReturn(expectedReceiver)
                .when(receiverService)
                .getById(receiverId.toString());
        Mockito.doThrow(expectedExtension)
                .when(videoService)
                .getVideoExById(videoId);

        String uri = getBaseUrl() + "/" + receiverId + command;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"id\":" + videoId + "}")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertsNotFound(mvcResult, expectedExtension);
        Mockito.verify(receiverService, Mockito.times(1)).getById(receiverId.toString());
    }

    /**
     * @param command example "/playVideo"
     */
    protected void sendCommandTestNotFoundReceiver(String command) throws Exception {
        final UUID receiverId = UUID.randomUUID();
        final long videoId = 1L;
        final ObjectNotFound expectedExtension = new ObjectNotFound(Video.class.getSimpleName(), receiverId.toString());
        Mockito.doThrow(expectedExtension)
                .when(receiverService)
                .getById(receiverId.toString());

        String uri = getBaseUrl() + "/" + receiverId + "/playVideo";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"id\":" + videoId + "}")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertsNotFound(mvcResult, expectedExtension);
        Mockito.verify(receiverService, Mockito.times(1)).getById(receiverId.toString());
    }

    protected void assertsNotFound(MvcResult mvcResult, ObjectNotFound objectNotFound) throws IOException {
        final int status = mvcResult.getResponse().getStatus();
        final String responseJson = mvcResult.getResponse().getContentAsString();
        final ApiError responseError = mapFromJson(responseJson, ApiError.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), status);
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), responseError.getStatus());
        assertEquals(objectNotFound.getMessage(), responseError.getMessage());
    }
}