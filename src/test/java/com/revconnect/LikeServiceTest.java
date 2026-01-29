package com.revconnect;

import com.revconnect.model.Post;
import com.revconnect.model.User;
import com.revconnect.repository.PostRepository;
import com.revconnect.repository.UserRepository;
import com.revconnect.service.LikeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
class LikeServiceTest {

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PostRepository postRepo;

    @Test
    void testDuplicateLikePrevention() {
        User user = new User();
        user.setEmail("like@gmail.com");
        user.setPassword("1234");
        userRepo.save(user);

        Post post = new Post();
        post.setUser(user);
        post.setContent("Test Post");
        postRepo.save(post);

        Scanner sc = new Scanner(post.getId() + "\n");
        String first = likeService.like(user, sc);

        Scanner sc2 = new Scanner(post.getId() + "\n");
        String second = likeService.like(user, sc2);

        assertEquals("❤️ Post liked", first);
        assertEquals("⚠ Already liked", second);
    }
}
