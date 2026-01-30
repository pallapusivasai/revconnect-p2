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

    /**
     * Send a notification to a user
     */
    public void send(User user, String message) {

        if (user == null || user.getId() == null) {
            logger.warn("Attempt to send notification to invalid user");
            throw new RevConnectException("Invalid user");
        }

        if (message == null || message.isBlank()) {
            logger.warn("Attempt to send empty notification to user {}", user.getEmail());
            throw new RevConnectException("Notification message cannot be empty");
        }

        try {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setMessage(message);
            notification.setSeen(false);

            repo.save(notification);

            logger.info("Notification sent to user {}", user.getEmail());

        } catch (Exception e) {
            logger.error(
                    "Failed to send notification to user {}",
                    user.getEmail(),
                    e
            );
            throw new RevConnectException("Failed to send notification");
        }
    }

    /**
     * View unread notifications (marks them as seen)
     */
    @Transactional
    public List<String> view(User user) {

        if (user == null || user.getId() == null) {
            logger.warn("Invalid notification view request");
            throw new RevConnectException("Invalid user");
        }

        try {
            List<Notification> notifications =
                    repo.findByUser_IdAndSeenFalse(user.getId());

            if (notifications.isEmpty()) {
                logger.info("No new notifications for {}", user.getEmail());
                return List.of();
            }

            // mark all as seen
            notifications.forEach(n -> n.setSeen(true));

            // batch save
            repo.saveAll(notifications);

            logger.info(
                    "User {} viewed {} notifications",
                    user.getEmail(),
                    notifications.size()
            );

            return notifications.stream()
                    .map(Notification::getMessage)
                    .toList();

        } catch (Exception e) {
            logger.error(
                    "Error while fetching notifications for user {}",
                    user.getEmail(),
                    e
            );
            throw new RevConnectException("Failed to load notifications");
        }
    }
}
