package com.revconnect;

import com.revconnect.model.Post;
import com.revconnect.model.User;
import com.revconnect.repository.PostRepository;
import com.revconnect.repository.UserRepository;
import com.revconnect.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test")
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PostRepository postRepo;

    @Test
    void testAddComment() {

        User user = new User();
        user.setEmail("comment@gmail.com");
        user.setPassword("1234");
        userRepo.save(user);

        Post post = new Post();
        post.setUser(user);
        post.setContent("My Post");
        postRepo.save(post);

        assertDoesNotThrow(() ->
                commentService.comment(user, post.getId(), "Nice post")
        );
    }
}
