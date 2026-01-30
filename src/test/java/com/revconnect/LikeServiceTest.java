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

import static org.junit.jupiter.api.Assertions.assertThrows;

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

        // create user
        User user = new User();
        user.setEmail("like@test.com");
        user.setPassword("1234");
        userRepo.save(user);

        // create post
        Post post = new Post();
        post.setUser(user);
        post.setContent("Test post");
        postRepo.save(post);

        Long postId = post.getId();

        // first like (should succeed)
        likeService.like(user, postId);

        // second like (should fail)
        assertThrows(RuntimeException.class, () ->
                likeService.like(user, postId)
        );
    }
}
