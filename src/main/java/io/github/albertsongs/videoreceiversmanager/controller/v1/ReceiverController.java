package io.github.albertsongs.videoreceiversmanager.controller.v1;

import io.github.albertsongs.videoreceiversmanager.exception.ReceiverNotFound;
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
    public Iterable<Receiver> getAllowedReceivers(HttpServletRequest request) {
        return receiverService.getAllWithLastIp(request.getRemoteAddr());
    }

    @GetMapping("/{receiverId}")
    public Receiver getReceiverById(@PathVariable(value = "receiverId") String receiverId) {
        return receiverService.getById(receiverId)
                .orElseThrow(() -> new ReceiverNotFound(receiverId));
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
