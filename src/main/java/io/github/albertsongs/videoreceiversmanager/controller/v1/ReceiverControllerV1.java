package io.github.albertsongs.videoreceiversmanager.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.albertsongs.videoreceiversmanager.controller.MessageController;
import io.github.albertsongs.videoreceiversmanager.exception.RequiredFieldIsEmpty;
import io.github.albertsongs.videoreceiversmanager.model.*;
import io.github.albertsongs.videoreceiversmanager.service.ReceiverService;
import io.github.albertsongs.videoreceiversmanager.service.VideoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static io.github.albertsongs.videoreceiversmanager.model.ReceiverCommandType.PLAY_YOUTUBE_VIDEO;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/receivers")
@AllArgsConstructor
public class ReceiverControllerV1 {
    protected final ReceiverService receiverService;
    protected final VideoService videoService;
    private final MessageController messageController;

    @PostMapping
    public Receiver createReceiver(HttpServletRequest request, @RequestBody Receiver receiver) {
        receiver.setLastIpAddress(request.getRemoteAddr());
        receiver.setUpdatedAt(new Date());
        return receiverService.add(receiver);
    }

    @GetMapping
    public ObjectListContainer<Receiver> getAllowedReceivers(
            HttpServletRequest request, @RequestParam(defaultValue = "false") boolean isOnline) {
        final Receiver exemplaryReceiver = new Receiver();
        exemplaryReceiver.setLastIpAddress(request.getRemoteAddr());
        final List<Receiver> receivers = receiverService.getAll(exemplaryReceiver, Sort.Order.desc("updatedAt"));
        return new ObjectListContainer<>(receivers);
    }

    @GetMapping("/{receiverId}")
    public Receiver getReceiverById(@PathVariable(value = "receiverId") String receiverId,
                                    @RequestParam(defaultValue = "false") boolean isOnline) {
        return receiverService.getById(receiverId);
    }

    @PatchMapping("/{receiverId}")
    public Receiver patchReceiver(HttpServletRequest request,
                                  @PathVariable(value = "receiverId") String receiverId,
                                  @RequestBody Receiver receiver) {
        receiver.setLastIpAddress(request.getRemoteAddr());
        receiver.setUpdatedAt(new Date());
        return receiverService.update(receiverId, receiver);
    }

    @DeleteMapping("/{receiverId}")
    //TODO: return HttpStatus.NO_CONTENT in APIv2
    public void deleteReceiverById(@PathVariable(value = "receiverId") String receiverId) {
        receiverService.deleteById(receiverId);
    }

    @PostMapping("/{receiverId}/playVideo")
    public ResponseEntity<HttpStatus> playVideoOnReceiverById(@PathVariable(value = "receiverId") String receiverId,
                                                              @RequestBody Video video) {
        checkReceiverBeforeSendCommand(receiverId);
        Long videoId = video.getId();
        if (videoId == null) {
            throw new RequiredFieldIsEmpty("id");
        }
        VideoEx videoExtendedInfo = videoService.getVideoExById(videoId);
        ReceiverCommand receiverCommand = new ReceiverCommand(PLAY_YOUTUBE_VIDEO, videoExtendedInfo);
        sendCommandToReceiver(receiverId, receiverCommand);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    protected void sendCommandToReceiver(String receiverId, ReceiverCommand command) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Message message = new Message(
                    "videoReceiverManager",
                    receiverId,
                    objectMapper.writeValueAsString(command),
                    Long.toString(new Date().getTime()),
                    Status.MESSAGE);
            messageController.recMessage(message);
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage());
        }
    }

    protected void checkReceiverBeforeSendCommand(String receiverId) {
        receiverService.getById(receiverId);
    }
}
