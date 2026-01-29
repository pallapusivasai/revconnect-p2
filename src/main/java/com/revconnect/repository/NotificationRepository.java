package com.revconnect.repository;
import com.revconnect.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface NotificationRepository extends JpaRepository<Notification,Long>{
    List<Notification> findByUserAndSeenFalse(User u);
}
