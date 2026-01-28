package com.revconnect;

import com.revconnect.model.User;
import com.revconnect.repository.UserRepository;
import com.revconnect.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepo;

    @Test
    void testCreatePost() {
        User user = new User();
        user.setEmail("post@gmail.com");
        user.setPassword("1234");
        userRepo.save(user);

        Scanner sc = new Scanner("Hello World");
        String result = postService.createPost(user, sc);

        assertEquals("✅ Post created", result);
    }
}
