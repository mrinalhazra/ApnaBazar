package com.ecomApp.notificationService.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class NotificationController {

    @MessageMapping("/sendMessage")
    @SendTo("/topic/notifications")   //in memory topic
    public String sendMessage(String message){
        System.out.println("Message : "+message);
        return message;
    }
}
