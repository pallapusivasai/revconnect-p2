package com.revconnect.repository;

import com.revconnect.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByUser_IdAndSeenFalse(Long userId);
}
