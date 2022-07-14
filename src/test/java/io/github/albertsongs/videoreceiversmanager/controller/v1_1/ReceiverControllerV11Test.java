package io.github.albertsongs.videoreceiversmanager.controller.v1_1;

import io.github.albertsongs.videoreceiversmanager.controller.v1.ReceiverControllerV1Test;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public
class ReceiverControllerV11Test extends ReceiverControllerV1Test {
    final String BASE_URL = "/api/v1.1/receivers";

    @Override
    protected String getBaseUrl() {
        return BASE_URL;
    }

    @Test
    void sendCommandPlayPauseToReceiverById() throws Exception {
        sendCommandTest("/play-pause");
    }

    @Test
    void sendCommandPlayPauseToReceiverByIdNotFoundReceiver() throws Exception {
        sendCommandTestNotFoundReceiver("/play-pause");
    }

    @Test
    void sendCommandNextToReceiverById() throws Exception {
        sendCommandTest("/next");
    }

    @Test
    void sendCommandNextToReceiverByIdNotFoundReceiver() throws Exception {
        sendCommandTestNotFoundReceiver("/next");
    }

    @Test
    void sendCommandPreviousToReceiverById() throws Exception {
        sendCommandTest("/previous");
    }

    @Test
    void sendCommandPreviousToReceiverByIdNotFoundReceiver() throws Exception {
        sendCommandTestNotFoundReceiver("/previous");
    }

    @Test
    void sendCommandVolumeUpToReceiverById() throws Exception {
        sendCommandTest("/volume/up");

    }

    @Test
    void sendCommandVolumeUpToReceiverByIdNotFoundReceiver() throws Exception {
        sendCommandTestNotFoundReceiver("/volume/up");

    }

    @Test
    void sendCommandVolumeDownToReceiverById() throws Exception {
        sendCommandTest("/volume/down");

    }

    @Test
    void sendCommandVolumeDownToReceiverByIdNotFoundReceiver() throws Exception {
        sendCommandTestNotFoundReceiver("/volume/down");
    }
}