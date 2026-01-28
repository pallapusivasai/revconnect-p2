package com.revconnect;

import com.revconnect.model.User;
import com.revconnect.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    void testRegisterAndLogin() {
        String result = authService.register("test@gmail.com", "1234");
        assertEquals("✅ Registered successfully", result);

        User user = authService.login("test@gmail.com", "1234");
        assertNotNull(user);
        assertEquals("test@gmail.com", user.getEmail());
    }
}
