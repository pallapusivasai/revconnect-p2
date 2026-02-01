package com.revconnect.controller;

import com.revconnect.model.Notification;
import com.revconnect.model.User;
import com.revconnect.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public List<Notification> getNotifications(@RequestBody User user) {
        return notificationService.getNotifications(user);
    }
}
