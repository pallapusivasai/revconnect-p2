package com.revconnect.service;

import com.revconnect.exception.RevConnectException;
import com.revconnect.model.Notification;
import com.revconnect.model.User;
import com.revconnect.repository.NotificationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {

    private static final Logger logger =
            LogManager.getLogger(NotificationService.class);

    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    public void send(User user, String message) {

        if (user == null || user.getId() == null ||
                message == null || message.isBlank()) {
            throw new RevConnectException("Invalid notification request");
        }

        Notification n = new Notification();
        n.setUser(user);
        n.setMessage(message);
        n.setSeen(false);

        repo.save(n);

        logger.info("Notification sent to user {}", user.getEmail());
    }

    // ✅ FIXED: transactional + batch update
    @Transactional
    public List<String> view(User user) {

        if (user == null || user.getId() == null) {
            throw new RevConnectException("Invalid user");
        }

        List<Notification> list =
                repo.findByUser_IdAndSeenFalse(user.getId());

        if (list.isEmpty()) {
            logger.info("No new notifications for {}", user.getEmail());
            return List.of();
        }

        // mark all as seen
        list.forEach(n -> n.setSeen(true));

        // save in ONE DB call
        repo.saveAll(list);

        logger.info("User {} viewed {} notifications",
                user.getEmail(), list.size());

        return list.stream()
                .map(Notification::getMessage)
                .toList();
    }
}
