package io.github.albertsongs.videoreceiversmanager.controller.v1_2;

import io.github.albertsongs.videoreceiversmanager.controller.MessageController;
import io.github.albertsongs.videoreceiversmanager.controller.v1_1.ReceiverControllerV1_1;
import io.github.albertsongs.videoreceiversmanager.model.ObjectListContainer;
import io.github.albertsongs.videoreceiversmanager.model.Receiver;
import io.github.albertsongs.videoreceiversmanager.service.ReceiverService;
import io.github.albertsongs.videoreceiversmanager.service.VideoService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.function.Predicate;

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
        final Predicate<Receiver> isLocalReceiver = receiver -> receiver.getLastIpAddress().equals(request.getRemoteAddr());
        final Comparator<Receiver> sortComparator = (r1, r2) -> r2.getUpdatedAt().compareTo(r1.getUpdatedAt());
        final Predicate<Receiver> filterPredicate = isOnline
                ? isLocalReceiver.and(receiver -> receiverService.isReceiverOnline(receiver.getId()))
                : isLocalReceiver;
        Iterable<Receiver> receivers = receiverService.getAll(filterPredicate, sortComparator);
        return new ObjectListContainer<>(receivers);
    }

    @Override
    protected void checkReceiverBeforeSendCommand(String receiverId) {
        receiverService.getOnlineReceiverById(receiverId);
    }
}
