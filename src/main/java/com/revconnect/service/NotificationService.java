package com.revconnect.service;

import com.revconnect.model.Notification;
import com.revconnect.model.User;
import com.revconnect.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    // ✅ RETURN data (no System.out)
    public List<String> view(User user) {

        var list = repo.findByUser_IdAndSeenFalse(user.getId());

        list.forEach(n -> {
            n.setSeen(true);
            repo.save(n);
        });

        return list.stream()
                .map(Notification::getMessage)
                .toList();
    }

}
