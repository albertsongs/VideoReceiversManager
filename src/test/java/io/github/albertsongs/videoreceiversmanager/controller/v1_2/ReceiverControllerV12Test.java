package io.github.albertsongs.videoreceiversmanager.controller.v1_2;


import io.github.albertsongs.videoreceiversmanager.controller.v1_1.ReceiverControllerV11Test;
import io.github.albertsongs.videoreceiversmanager.exception.ObjectNotFound;
import io.github.albertsongs.videoreceiversmanager.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReceiverControllerV12Test extends ReceiverControllerV11Test {
    final String BASE_URL = "/api/v1.2/receivers";

    @Override
    protected String getBaseUrl() {
        return BASE_URL;
    }

    @Test
    void getAllowedOnlineReceivers() throws Exception {
        final List<Receiver> receivers = buildReceivers();
        Mockito.doReturn(receivers)
                .when(receiverService)
                .getAll(Mockito.any(Receiver.class), Mockito.any(Sort.Order.class));
        Mockito.doReturn(true)
                .when(receiverService)
                .isReceiverOnline(Mockito.any(Receiver.class));
        receivers.sort((r1, r2) -> r2.getUpdatedAt().compareTo(r1.getUpdatedAt()));
        ObjectListContainer<Receiver> expectedVideosContainer = new ObjectListContainer<>(receivers);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                .queryParam("isOnline", "true")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        final int status = mvcResult.getResponse().getStatus();
        final String responseJson = mvcResult.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), status);
        assertEquals(mapToJson(expectedVideosContainer), responseJson);
        Mockito.verify(receiverService, Mockito.times(1))
                .getAll(Mockito.any(Receiver.class), Mockito.any(Sort.Order.class));
    }

    @Test
    void getAllowedOfflineReceivers() throws Exception {
        final List<Receiver> receivers = buildReceivers();
        Mockito.doReturn(receivers)
                .when(receiverService)
                .getAll(Mockito.any(Receiver.class), Mockito.any(Sort.Order.class));
        receivers.sort((r1, r2) -> r2.getUpdatedAt().compareTo(r1.getUpdatedAt()));
        ObjectListContainer<Receiver> expectedVideosContainer = new ObjectListContainer<>(receivers);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                .queryParam("isOnline", "false")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        final int status = mvcResult.getResponse().getStatus();
        final String responseJson = mvcResult.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), status);
        assertEquals(mapToJson(expectedVideosContainer), responseJson);
        Mockito.verify(receiverService, Mockito.times(1))
                .getAll(Mockito.any(Receiver.class), Mockito.any(Sort.Order.class));
    }

    @Test
    void getOnlineReceiverById() throws Exception {
        final UUID receiverId = UUID.randomUUID();
        final Receiver expectedReceiver = buildReceiver(receiverId);
        Mockito.doReturn(expectedReceiver)
                .when(receiverService)
                .getOnlineReceiverById(receiverId.toString());

        String uri = getBaseUrl() + "/" + receiverId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .queryParam("isOnline", "true")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        final int status = mvcResult.getResponse().getStatus();
        final String responseJson = mvcResult.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), status);
        assertEquals(mapToJson(expectedReceiver), responseJson);
        Mockito.verify(receiverService, Mockito.times(1)).getOnlineReceiverById(receiverId.toString());
    }

    @Test
    void getOfflineReceiverById() throws Exception {
        final UUID receiverId = UUID.randomUUID();
        final Receiver expectedReceiver = buildReceiver(receiverId);
        Mockito.doReturn(expectedReceiver)
                .when(receiverService)
                .getById(receiverId.toString());

        String uri = getBaseUrl() + "/" + receiverId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .queryParam("isOnline", "false")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        final int status = mvcResult.getResponse().getStatus();
        final String responseJson = mvcResult.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), status);
        assertEquals(mapToJson(expectedReceiver), responseJson);
        Mockito.verify(receiverService, Mockito.times(1)).getById(receiverId.toString());
    }

    @Test
    void getReceiverByIdNotFound() throws Exception {
        final UUID receiverId = UUID.randomUUID();
        ObjectNotFound objectNotFound = new ObjectNotFound(Receiver.class.getSimpleName(), receiverId.toString());
        Mockito.doThrow(objectNotFound)
                .when(receiverService)
                .getOnlineReceiverById(receiverId.toString());

        String uri = getBaseUrl() + "/" + receiverId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .queryParam("isOnline", "true")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertsNotFound(mvcResult, objectNotFound);
        Mockito.verify(receiverService, Mockito.times(1)).getOnlineReceiverById(receiverId.toString());
    }

    /**
     *
     */
    @Override
    protected void sendCommandTestNotFoundVideo() throws Exception {
        final UUID receiverId = UUID.randomUUID();
        final Long videoId = 1L;
        final Receiver expectedReceiver = buildReceiver(receiverId);
        final ObjectNotFound expectedExtension = new ObjectNotFound(Video.class.getSimpleName(), videoId.toString());
        Mockito.doReturn(expectedReceiver)
                .when(receiverService)
                .getOnlineReceiverById(receiverId.toString());
        Mockito.doThrow(expectedExtension)
                .when(videoService)
                .getVideoExById(videoId);

        String uri = getBaseUrl() + "/" + receiverId + "/playVideo";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"id\":" + videoId + "}")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertsNotFound(mvcResult, expectedExtension);
        Mockito.verify(receiverService, Mockito.times(1))
                .getOnlineReceiverById(receiverId.toString());
    }

    /**
     * @param command example "/playVideo"
     */
    @Override
    protected void sendCommandTest(String command) throws Exception {
        final UUID receiverId = UUID.randomUUID();
        final Long videoId = 1L;
        final VideoEx video = new VideoEx(videoId,
                "test", "http://localhost/test.webm",
                "http://localhost/test.vtt", new VideoMeta("vIdeOiD", "PlAylIsTiD"));
        final Receiver expectedReceiver = buildReceiver(receiverId);
        Mockito.doReturn(expectedReceiver)
                .when(receiverService)
                .getOnlineReceiverById(receiverId.toString());
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
        Mockito.verify(receiverService, Mockito.times(1))
                .getOnlineReceiverById(receiverId.toString());
    }

    /**
     * @param command example "/playVideo"
     */
    @Override
    protected void sendCommandTestNotFoundReceiver(String command) throws Exception {
        final UUID receiverId = UUID.randomUUID();
        final long videoId = 1L;
        final ObjectNotFound expectedExtension = new ObjectNotFound(Video.class.getSimpleName(), receiverId.toString());
        Mockito.doThrow(expectedExtension)
                .when(receiverService)
                .getOnlineReceiverById(receiverId.toString());

        String uri = getBaseUrl() + "/" + receiverId + "/playVideo";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"id\":" + videoId + "}")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertsNotFound(mvcResult, expectedExtension);
        Mockito.verify(receiverService, Mockito.times(1))
                .getOnlineReceiverById(receiverId.toString());
    }
}
