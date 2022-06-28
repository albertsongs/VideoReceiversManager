package io.github.albertsongs.videoreceiversmanager.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.albertsongs.videoreceiversmanager.exception.RequiredFieldIsEmpty;
import io.github.albertsongs.videoreceiversmanager.model.*;
import io.github.albertsongs.videoreceiversmanager.service.ReceiverService;
import io.github.albertsongs.videoreceiversmanager.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.StreamSupport;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/receivers")
@CrossOrigin("https://albertsongs.github.io")
public final class ReceiverController {
    @Autowired
    private ReceiverService receiverService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private MessageController messageController;

    @PostMapping
    public Receiver createReceiver(HttpServletRequest request, @RequestBody Receiver receiver) {
        receiver.setLastIpAddress(request.getRemoteAddr());
        receiver.setUpdatedAt(new Date());
        return receiverService.add(receiver);
    }

    @GetMapping
    public ObjectListContainer<Receiver> getAllowedReceivers(HttpServletRequest request) {
        ObjectListContainer<Receiver> receiversContainer = new ObjectListContainer<>();
        Iterable<Receiver> receivers = receiverService.getAllWithLastIp(request.getRemoteAddr());
        receiversContainer.setList(StreamSupport.stream(receivers.spliterator(), false)
                .sorted((r1, r2) -> r2.getUpdatedAt().compareTo(r1.getUpdatedAt()))
                .toList());
        return receiversContainer;
    }

    @GetMapping("/{receiverId}")
    public Receiver getReceiverById(@PathVariable(value = "receiverId") String receiverId) {
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
    public void deleteReceiverById(@PathVariable(value = "receiverId") String receiverId) {
        receiverService.deleteById(receiverId);
    }

    @PostMapping("/{receiverId}/playVideo")
    public ResponseEntity<HttpStatus> playVideoOnReceiverById(@PathVariable(value = "receiverId") String receiverId,
                                          @RequestBody Video video) {
        receiverService.getById(receiverId);
        Long videoId = video.getId();
        if (videoId == null) {
            throw new RequiredFieldIsEmpty("id");
        }
        sendVideoInfoToReceiver(receiverId, videoService.getVideoExById(videoId));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private void sendVideoInfoToReceiver(String receiverId, Video videoInfo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Message message = new Message(
                    "server",
                    receiverId,
                    objectMapper.writeValueAsString(videoInfo),
                    Long.toString(new Date().getTime()),
                    Status.MESSAGE);
            messageController.recMessage(message);
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage());
        }
    }
}
