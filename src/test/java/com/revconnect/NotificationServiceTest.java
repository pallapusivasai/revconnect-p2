package java.com.revconnect;

import com.revconnect.model.User;
import com.revconnect.repository.UserRepository;
import com.revconnect.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test")
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

        assertDoesNotThrow(() -> notificationService.view(user));
        assertDoesNotThrow(() -> notificationService.view(user));
    }
}
