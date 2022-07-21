package io.github.albertsongs.videoreceiversmanager.controller.v1_1;

import io.github.albertsongs.videoreceiversmanager.controller.MessageController;
import io.github.albertsongs.videoreceiversmanager.controller.v1.ReceiverControllerV1;
import io.github.albertsongs.videoreceiversmanager.exception.RequiredFieldIsEmpty;
import io.github.albertsongs.videoreceiversmanager.model.ReceiverCommand;
import io.github.albertsongs.videoreceiversmanager.model.Video;
import io.github.albertsongs.videoreceiversmanager.service.ReceiverService;
import io.github.albertsongs.videoreceiversmanager.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.github.albertsongs.videoreceiversmanager.model.ReceiverCommandType.*;

@Slf4j
@RestController
@RequestMapping(value = "api/v1.1/receivers")
public class ReceiverControllerV1_1 extends ReceiverControllerV1 {
    public ReceiverControllerV1_1(ReceiverService receiverService, VideoService videoService,
                                  MessageController messageController) {
        super(receiverService, videoService, messageController);
    }

    @Override
    @PostMapping("/{receiverId}/playVideo")
    public ResponseEntity<HttpStatus> playVideoOnReceiverById(@PathVariable(value = "receiverId") String receiverId,
                                                              @RequestBody Video video) {
        checkReceiverBeforeSendCommand(receiverId);
        Long videoId = video.getId();
        String videoUrl = video.getUrl();
        if (videoId == null && videoUrl == null) {
            throw new RequiredFieldIsEmpty("id");
        }
        ReceiverCommand receiverCommand = videoUrl == null
                ? new ReceiverCommand(PLAY_VIDEO, videoService.getVideoExById(videoId))
                : new ReceiverCommand(PLAY_VIDEO, video);
        sendCommandToReceiver(receiverId, receiverCommand);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{receiverId}/play-pause")
    public ResponseEntity<HttpStatus> sendCommandPlayPauseToReceiverById(
            @PathVariable(value = "receiverId") String receiverId) {
        checkReceiverBeforeSendCommand(receiverId);
        sendCommandToReceiver(receiverId, new ReceiverCommand(PLAY_PAUSE, null));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{receiverId}/next")
    public ResponseEntity<HttpStatus> sendCommandNextToReceiverById(
            @PathVariable(value = "receiverId") String receiverId) {
        checkReceiverBeforeSendCommand(receiverId);
        sendCommandToReceiver(receiverId, new ReceiverCommand(NEXT, null));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{receiverId}/previous")
    public ResponseEntity<HttpStatus> sendCommandPreviousToReceiverById(
            @PathVariable(value = "receiverId") String receiverId) {
        checkReceiverBeforeSendCommand(receiverId);
        sendCommandToReceiver(receiverId, new ReceiverCommand(PREVIOUS, null));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{receiverId}/volume/up")
    public ResponseEntity<HttpStatus> sendCommandVolumeUpToReceiverById(
            @PathVariable(value = "receiverId") String receiverId) {
        checkReceiverBeforeSendCommand(receiverId);
        sendCommandToReceiver(receiverId, new ReceiverCommand(VOLUME_UP, null));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{receiverId}/volume/down")
    public ResponseEntity<HttpStatus> sendCommandVolumeDownToReceiverById(
            @PathVariable(value = "receiverId") String receiverId) {
        checkReceiverBeforeSendCommand(receiverId);
        sendCommandToReceiver(receiverId, new ReceiverCommand(VOLUME_DOWN, null));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
