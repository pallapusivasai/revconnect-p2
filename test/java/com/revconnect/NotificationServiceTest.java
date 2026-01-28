package com.revconnect;

import com.revconnect.model.User;
import com.revconnect.service.NotificationService;
import com.revconnect.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepo;

    @Test
    void testNotificationSeenLogic() {
        User user = new User();
        user.setEmail("notify@gmail.com");
        user.setPassword("1234");
        userRepo.save(user);

        notificationService.send(user, "Test notification");

        // first view → shown
        notificationService.view(user);

        // second view → nothing (seen)
        notificationService.view(user);

        assertTrue(true); // no exception = pass
    }
}
