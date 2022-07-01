package io.github.albertsongs.videoreceiversmanager.controller.v1_1;

import io.github.albertsongs.videoreceiversmanager.controller.v1.ReceiverController;
import io.github.albertsongs.videoreceiversmanager.exception.RequiredFieldIsEmpty;
import io.github.albertsongs.videoreceiversmanager.model.ReceiverCommand;
import io.github.albertsongs.videoreceiversmanager.model.Video;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.github.albertsongs.videoreceiversmanager.model.ReceiverCommandType.PLAY_VIDEO;

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
        if (videoId == null && videoUrl.isEmpty()) {
            throw new RequiredFieldIsEmpty("id");
        }
        ReceiverCommand receiverCommand = videoUrl.isEmpty()
                ? new ReceiverCommand(PLAY_VIDEO, videoService.getVideoExById(videoId))
                : new ReceiverCommand(PLAY_VIDEO, video);
        sendCommandToReceiver(receiverId, receiverCommand);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
