package com.revconnect.service;

import com.revconnect.exception.RevConnectException;
import com.revconnect.model.Post;
import com.revconnect.model.User;
import com.revconnect.repository.PostRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private static final Logger logger =
            LogManager.getLogger(PostService.class);

    private final PostRepository repo;

    public PostService(PostRepository repo) {
        this.repo = repo;
    }

    // Service performs business logic only
    public void createPost(User user, String content) {

        if (user == null || content == null || content.isBlank()) {
            throw new RevConnectException("Invalid post content");
        }

        Post post = new Post();
        post.setUser(user);
        post.setContent(content);

        repo.save(post);

        logger.info("Post created by {}", user.getEmail());
    }

    public List<Post> viewMyPosts(User user) {

        if (user == null) {
            throw new RevConnectException("Invalid user");
        }

        logger.info("Viewing posts for user {}", user.getEmail());
        return repo.findByUser(user);
    }

    public List<Post> viewFeed() {
        logger.info("Viewing feed");
        return repo.findAllByOrderByIdDesc();
    }
}
