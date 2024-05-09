package com.myapp.ecommerce.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class MessageController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/api/messages")
    public void sendMessage(@RequestBody String message) {

        messagingTemplate.convertAndSend("/topic/cart", message);
    }

    public void sendMessageToWebSocket(String message) {
        log.info("Mandando mensagem ------------------------------------------ ");
        messagingTemplate.convertAndSend("/topic/cart", message);
    }
}