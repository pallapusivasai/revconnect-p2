package com.revconnect;

import com.revconnect.model.Post;
import com.revconnect.model.User;
import com.revconnect.repository.PostRepository;
import com.revconnect.repository.UserRepository;
import com.revconnect.service.LikeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        // Arrange: create user
        User user = new User();
        user.setEmail("like@gmail.com");
        user.setPassword("1234");
        userRepo.save(user);

        // Arrange: create post
        Post post = new Post();
        post.setUser(user);
        post.setContent("Test Post");
        postRepo.save(post);

        Long postId = post.getId();

        // Act: like twice
        String first = likeService.like(user, postId);
        String second = likeService.like(user, postId);

        // Assert
        assertEquals("❤️ Post liked", first);
        assertEquals("⚠ Already liked", second);
    }
}
