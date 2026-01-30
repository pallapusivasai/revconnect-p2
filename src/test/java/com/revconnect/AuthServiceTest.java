package java.com.revconnect;

import com.revconnect.exception.RevConnectException;
import com.revconnect.model.User;
import com.revconnect.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    void testRegisterAndLogin() {

        assertDoesNotThrow(() ->
                authService.register("test@gmail.com", "1234")
        );

        User user = authService.login("test@gmail.com", "1234");

        assertNotNull(user);
        assertEquals("test@gmail.com", user.getEmail());
    }

    @Test
    void testDuplicateRegister() {

        authService.register("dup@gmail.com", "1234");

        assertThrows(
                RevConnectException.class,
                () -> authService.register("dup@gmail.com", "1234")
        );
    }
}
