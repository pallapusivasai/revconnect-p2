package java.com.revconnect;

import com.revconnect.exception.RevConnectException;
import com.revconnect.model.User;
import com.revconnect.repository.UserRepository;
import com.revconnect.service.FollowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class FollowServiceTest {

    @Autowired
    private FollowService followService;

    @Autowired
    private UserRepository userRepo;

    @Test
    void testFollowAndUnfollow() {

        User u1 = new User();
        u1.setEmail("a@gmail.com");
        u1.setPassword("1234");
        userRepo.save(u1);

        User u2 = new User();
        u2.setEmail("b@gmail.com");
        u2.setPassword("1234");
        userRepo.save(u2);

        assertDoesNotThrow(() ->
                followService.follow(u1, u2.getId())
        );

        assertDoesNotThrow(() ->
                followService.unfollow(u1, u2.getId())
        );
    }

    @Test
    void testDuplicateFollow() {

        User u1 = new User();
        u1.setEmail("c@gmail.com");
        u1.setPassword("1234");
        userRepo.save(u1);

        User u2 = new User();
        u2.setEmail("d@gmail.com");
        u2.setPassword("1234");
        userRepo.save(u2);

        followService.follow(u1, u2.getId());

        assertThrows(
                RevConnectException.class,
                () -> followService.follow(u1, u2.getId())
        );
    }
}
