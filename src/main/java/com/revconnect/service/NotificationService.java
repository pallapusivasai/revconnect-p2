package com.revconnect.service;

import com.revconnect.model.Notification;
import com.revconnect.model.User;
import com.revconnect.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    public void send(User user, String message) {

        Notification n = new Notification();
        n.setUser(user);
        n.setMessage(message);
        n.setSeen(false);

        repo.save(n);
    }

    public void view(User user) {

        System.out.println("\n=== Notifications ===");

        repo.findByUserAndSeenFalse(user).forEach(n -> {
            System.out.println("🔔 " + n.getMessage());
            n.setSeen(true);
            repo.save(n);
        });
    }
}
