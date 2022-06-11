package io.github.albertsongs.videoreceiversmanager.controller.v1;

import io.github.albertsongs.videoreceiversmanager.exception.ObjectNotFound;
import io.github.albertsongs.videoreceiversmanager.model.ObjectList;
import io.github.albertsongs.videoreceiversmanager.model.Receiver;
import io.github.albertsongs.videoreceiversmanager.service.ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "api/v1/receivers")
public final class ReceiverController {
    @Autowired
    private ReceiverService receiverService;

    @PostMapping
    public Receiver createReceiver(HttpServletRequest request, @RequestBody @Validated Receiver receiver) {
        return receiverService.add(receiver, request.getRemoteAddr());
    }

    @GetMapping
    public ObjectList<Receiver> getAllowedReceivers(HttpServletRequest request) {
        ObjectList<Receiver> receivers = new ObjectList<>();
        receivers.setList(receiverService.getAllWithLastIp(request.getRemoteAddr()));
        return receivers;
    }

    @GetMapping("/{receiverId}")
    public Receiver getReceiverById(@PathVariable(value = "receiverId") String receiverId) {
        return receiverService.getById(receiverId)
                .orElseThrow(() -> new ObjectNotFound(Receiver.class.getSimpleName(), receiverId));
    }

    @PatchMapping("/{receiverId}")
    public Receiver patchReceiver(@PathVariable(value = "receiverId") String receiverId,
                                  @RequestBody Receiver receiver) {
        return receiverService.update(receiverId, receiver);
    }

    @DeleteMapping("/{receiverId}")
    public void deleteReceiver(@PathVariable(value = "receiverId") String receiverId) {
        receiverService.delete(receiverId);
    }
}
