package io.github.albertsongs.videoreceiversmanager.controller.v1_1;

import io.github.albertsongs.videoreceiversmanager.controller.v1.ReceiverController;
import io.github.albertsongs.videoreceiversmanager.exception.RequiredFieldIsEmpty;
import io.github.albertsongs.videoreceiversmanager.model.ReceiverCommand;
import io.github.albertsongs.videoreceiversmanager.model.Video;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.github.albertsongs.videoreceiversmanager.model.ReceiverCommandType.*;

@Slf4j
@RestController
@RequestMapping(value = "api/v1.1/receivers")
public class ReceiverControllerV1_1 extends ReceiverController {
    @PostMapping("/{receiverId}/playVideo")
    @Override
    public ResponseEntity<HttpStatus> playVideoOnReceiverById(@PathVariable(value = "receiverId") String receiverId,
                                                              @RequestBody Video video) {
        receiverService.getById(receiverId);
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
    public ResponseEntity<HttpStatus> sendCommandPlayToReceiverById(
            @PathVariable(value = "receiverId") String receiverId) {
        receiverService.getById(receiverId);
        sendCommandToReceiver(receiverId, new ReceiverCommand(PLAY, null));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{receiverId}/next")
    public ResponseEntity<HttpStatus> sendCommandNextToReceiverById(
            @PathVariable(value = "receiverId") String receiverId) {
        receiverService.getById(receiverId);
        sendCommandToReceiver(receiverId, new ReceiverCommand(NEXT, null));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{receiverId}/previous")
    public ResponseEntity<HttpStatus> sendCommandPreviousToReceiverById(
            @PathVariable(value = "receiverId") String receiverId) {
        receiverService.getById(receiverId);
        sendCommandToReceiver(receiverId, new ReceiverCommand(PREVIOUS, null));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{receiverId}/volume/up")
    public ResponseEntity<HttpStatus> sendCommandVolumeUpToReceiverById(
            @PathVariable(value = "receiverId") String receiverId) {
        receiverService.getById(receiverId);
        sendCommandToReceiver(receiverId, new ReceiverCommand(VOLUME_UP, null));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{receiverId}/volume/down")
    public ResponseEntity<HttpStatus> sendCommandVolumeDownToReceiverById(
            @PathVariable(value = "receiverId") String receiverId) {
        receiverService.getById(receiverId);
        sendCommandToReceiver(receiverId, new ReceiverCommand(VOLUME_UP, null));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
