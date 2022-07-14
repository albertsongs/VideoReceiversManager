package io.github.albertsongs.videoreceiversmanager.controller;

import io.github.albertsongs.videoreceiversmanager.model.Message;
import io.github.albertsongs.videoreceiversmanager.service.ReceiverService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin("https://albertsongs.github.io")
@AllArgsConstructor
public class MessageController {
    private SimpMessagingTemplate simpMessagingTemplate;
    private ReceiverService receiverService;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message) {
        String senderName = message.getSenderName();
        String messageContent = message.getMessage();
        if (senderName != null && messageContent != null && (messageContent.equals("RESPOND"))) {
            receiverService.handleRespondReceiver(senderName);
        }
        return message;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
        System.out.println(message);
        return message;
    }
}
