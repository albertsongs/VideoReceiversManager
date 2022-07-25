package io.github.albertsongs.videoreceiversmanager.controller.v1_2;

import io.github.albertsongs.videoreceiversmanager.controller.MessageController;
import io.github.albertsongs.videoreceiversmanager.controller.v1_1.ReceiverControllerV1_1;
import io.github.albertsongs.videoreceiversmanager.model.ObjectListContainer;
import io.github.albertsongs.videoreceiversmanager.model.Receiver;
import io.github.albertsongs.videoreceiversmanager.service.ReceiverService;
import io.github.albertsongs.videoreceiversmanager.service.VideoService;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1.2/receivers")
public class ReceiverControllerV1_2 extends ReceiverControllerV1_1 {
    public ReceiverControllerV1_2(ReceiverService receiverService, VideoService videoService,
                                  MessageController messageController) {
        super(receiverService, videoService, messageController);
    }

    @Override
    @GetMapping("/{receiverId}")
    public Receiver getReceiverById(@PathVariable(value = "receiverId") String receiverId,
                                    @RequestParam(defaultValue = "false") boolean isOnline) {
        return isOnline
                ? receiverService.getOnlineReceiverById(receiverId)
                : receiverService.getById(receiverId);
    }

    @Override
    @GetMapping
    public ObjectListContainer<Receiver> getAllowedReceivers(
            HttpServletRequest request, @RequestParam(defaultValue = "false") boolean isOnline) {
        final Receiver exemplaryReceiver = new Receiver();
        exemplaryReceiver.setLastIpAddress(request.getRemoteAddr());
        final List<Receiver> receivers = receiverService.getAll(exemplaryReceiver, Sort.Order.desc("updatedAt"));
        if (isOnline) {
            return new ObjectListContainer<>(receivers.stream()
                    .filter(receiverService::isReceiverOnline).toList());
        }
        return new ObjectListContainer<>(receivers);
    }

    @Override
    protected void checkReceiverBeforeSendCommand(String receiverId) {
        receiverService.getOnlineReceiverById(receiverId);
    }
}
